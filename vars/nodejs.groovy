def lintChecks(){
    sh '''
        echo Installing JSLint for ${COMPONENT}
        npm i jslint
        node_modules/jslint/bin/jslint.js server.js || true
        echo Installing lint checks done for the ${COMPONENT}
    '''
}


def call (COMPONENT) {
    pipeline {
        agent { label 'work-station' }
        environment {
            SONARCRED = credentials('SONARCRED')
            SONARURL = "172.31.89.102"
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
                        sh "npm install"
                }
            }

            stage('Sonar Checks') {
                steps {
                    script {  
                        env.ARGS="-Dsonar.sources=."
                        common.sonarChecks()
                    }                                // calling the sonar checks function from line11
                }
            }

            stage('TestCases') {
                parallel {
                    stage ('Unit Testing') {
                        steps {
                            sh "echo unit testing started"
                            sh "echo unit testing done"
                        }
                    }
                    stage ('Integration Testing') {
                        steps {
                            sh "echo Integration testing started"
                            sh "echo Integration testing done"
                        }
                    }
                    stage ('Funtional Testing') {
                        steps {
                            sh "echo Functional testing started"
                            sh "echo Functional testing done"
                        }
                    }

                }
            }                                                                      
        
        }
    }  
}


///







//                     sh "echo installing JSlint"
//                     sh "npm i jslint"
//                     sh "node_modules/jslint/bin/jslint.js server.js || true"   // this double pipe symbol includes that if the first command is failure make it as a true statement  (that pipe symbol makes forece fully passes the stage)


// def info(message) {
//     echo "hello I'm a function and my name is info"
//     echo "I'm printing the value of message is ${message}"
// }

// // calling the function
// info("hi")
