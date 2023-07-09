def lintChecks(){
    stage('Lint Checks') {
        if(env.APP_TYPE == "nodejs") {
        sh  ''' 
           echo Installing JSLint for ${COMPONENT}
           npm i jslint  
           node_modules/jslint/bin/jslint.js server.js || true
           echo lint checks completed for ${COMPONENT}        
           ''' 
        }
        else if(env.APP_TYPE == "python") {
        sh '''
            # pip3 install pylint
            # pip *.py
            echo lint checks started for ${COMPONENT} using pytlint
            echo lint checks completed for ${COMPONENT}  
        ''' 
        }
        else if(env.APP_TYPE == "java") {
        sh '''
            echo lint checks started for ${COMPONENT} using mvn
            # mvn checkstyle:check
            echo lint checks completed for ${COMPONENT}  
        ''' 
        }
        else  {
        sh '''
            echo lint checks started for ${COMPONENT} using angular js
            echo lint checks completed for ${COMPONENT}  
         ''' 
        }
    }
}


def sonarChecks(){
       stage ('sonar checks') {
          sh "echo Sonar Checks starts"
          // sh "sonar-scanner -Dsonar.host.url=http://172.31.89.102:9000  -Dsonar.projectKey=${COMPONENT} ${ARGS} -Dsonar.login=admin -Dsonar.password=password"
          // sh "curl https://gitlab.com/thecloudcareers/opensource/-/raw/master/lab-tools/sonar-scanner/quality-gate > sonar-quality-gate.sh"
          // sh "sonar-quality-gate.sh ${SONARCRED_USR} ${SONARCRED_PSW} ${SONARURL} ${COMPONENT}"
          sh "echo Sonar checks done"
       }
}

// Below is the way for the scripted pipeline for parallel stages


def testCases() {
        stage('Test Cases') {
                def stages = [:]

                stages["Unit Testing"] = {
                        echo "Unit Testing has Started for ${COMPONENT}"
                        echo "Unit Testing is Completed for ${COMPONENT}"
                }
                stages["Integration Testing"] = {
                        echo "Integration Testing has Started for ${COMPONENT}"
                        echo "Integration Testing is  Completed for ${COMPONENT}"
                }
                stages["Functional Testing"] = {
                        echo "Functional Testing has Started for ${COMPONENT}"
                        echo "Functional Testing is Completed for ${COMPONENT}"
                }

                parallel(stages)
        }                        
}




// def sonarChecks(){                            // related to shared library.
//        sh "echo Sonar Checks starts"
//        sh "echo Sonar checks done"
// }