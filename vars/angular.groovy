def lintChecks(){
    sh '''
        echo Installing AngularLint for ${COMPONENT}
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

            stage('Code ComQualitypile') {
                steps {
                        //sh "npm install"
                }
            }                                              

        }
    }
}


