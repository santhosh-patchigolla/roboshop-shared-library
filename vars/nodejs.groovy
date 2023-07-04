def lintChecks(){
    sh '''
        echo Installing JSLint for ${COMPONENT}
        npm i jslint
        node_modules/jslint/bin/jslint.js server.js || true
        echo Installing lint checks done for the ${COMPONENT}

    '''
}

def sonarChecks(){
    sh '''
        echo Sonar Checks starts
        sonar-scanner -Dsonar.sources=. -Dsonar.login=0d48104d8af75f393eec3c505f8f0cb743363849 -Dsonar.host.url=http://172.31.89.102:9000  -Dsonar.projectKey=${COMPONENT}
        curl https://gitlab.com/thecloudcareers/opensource/-/raw/master/lab-tools/sonar-scanner/quality-gate > sonar-quality-gate.sh
        sonar-quality-gate.sh ${SONARCRED_USR} ${SONARCRED_PSW} ${SONATURL} ${COMPONENT}
        echo Sonar checks done

    '''
}


def call (COMPONENT) {
    pipeline {
        agent { label 'work-station' }
        enviornment {
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
                        sh "npm install"
                }
            }

            stage('Sonar Checks') {
                steps {
                    script {
                        sonarChecks()
                    }                     // calling the sonar checks function from line11
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
