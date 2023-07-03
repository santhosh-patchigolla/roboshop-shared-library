def lintChecks(){
    sh '''
        echo Installing JSLint
        npm i jslint
        node_modules/jslint/bin/jslint.js server.js || true

    '''
}


def call () {
    pipeline {
        agent { label 'work-station' }
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
