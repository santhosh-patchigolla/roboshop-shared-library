def lintChecks(){
    sh '''
        echo Installing AngularLint for ${COMPONENT}
        echo Installing lint checks done for the ${COMPONENT}

    '''
}


def call (COMPONENT) {
    pipeline {
        agent { label 'work-station' }
        environment {
            SONARCRED = credentials('SONARCRED') 
            SONATURL  = "172.31.89.102"
        }        
        stages {

            stage ('Lint Check') {                     
                steps {
                    script {
                        lintChecks()
                    }
                }    
            }

            stage('Code Quality') {
                steps {
                        sh "echo Code Quality Analysis"
                }
            }                                              

        }
    }
}


