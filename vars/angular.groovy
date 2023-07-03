def lintChecks(){
    sh '''
        echo Installing JSLint for ${COMPONENT}
        echo Installing lint checks done for the ${COMPONENT}

    '''
}


def call (COMPONENT) {
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
                        //sh "npm install"
                }
            }                                              
        }
    }
}


