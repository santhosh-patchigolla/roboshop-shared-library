def lintChecks(){
    sh '''
        echo doing lintChecks for ${COMPONENT}
        mvn checkstyle:check || true       
        echo lintChcecks done for the ${COMPONENT}

    '''
}


def call (COMPONENT) {
    pipeline {
        agent { label 'work-station' }
        environment {  
            SONARCRED = credentials('SONARCRED')
            SONATURL = "172.31.89.102"
        }
        stages {

            stage ('Lint Check') {                     
                steps {
                    script {
                        lintChecks()
                    }
                }    
            }

            stage('Code Compile') {
                steps {
                        sh "mvn clean compile"
                }
            }

            stage('Sonar Checks') {
                steps {
                    script {
                        env.ARGS="-Dsonar.java.binaries=target/"
                        common.sonarChecks()
                    }                                // calling the sonar checks function from line11
                }
            }

            stage('Testing') {
                steps {
                        sh "echo Testing in progress"
                }
            }             

        }
    }
}










//                     sh "echo installing JSlint"
//                     sh "npm i jslint"
//                     sh "node_modules/jslint/bin/jslint.js server.js || true"   // this double pipe symbol includes that if the first command is failure make it as a true statement  (that pipe symbol makes forece fully passes the stage)


// def info(message) {
//     echo "hello I'm a function and my name is info"
//     echo "I'm printing the value of message is ${message}"
// }

// // calling the function
// info("hi")
