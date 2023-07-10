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



def artifacts() {                  // in groov we need to declare the fucntion in the "def"

        stage('Validate Artifact Version') {
            env.UPLOAD_STATUS=sh (returnStdout: true, script: 'curl -L -s  http://${NEXUSURL}:8081/service/rest/repository/browse/${COMPONENT}/ | grep ${COMPONENT}-${TAG_NAME} || true' )   
            print UPLOAD_STATUS
        }                    
                
        if(env.UPLOAD_STATUS == "") {
                stage('Prepare Artifacts') {
                        if(env.APP_TYPE == "nodejs"){
                                sh '''
                                        echo Preparing Artifacts for ${COMPONENT}
                                        npm install
                                        zip -r ${COMPONENT}-${TAG_NAME}.zip node_modules server.js                        
                                '''
                        }
                        else if(env.APP_TYPE == "python"){
                                sh '''
                                        echo Preparing Artifacts for ${COMPONENT}
                                        zip -r ${COMPONENT}-${TAG_NAME}.zip *.py *.int  requirements.txt                     
                                '''
                        }
                        else if(env.APP_TYPE == "java"){
                                sh '''
                                        echo Preparing Artifacts for ${COMPONENT}
                                        mvn clean package
                                        mv target/${COMPONENT}-1.0.jar ${COMPONENT}.jar
                                        zip -r ${COMPONENT}-${TAG_NAME}.zip  ${COMPONENT}.jar          
                                '''
                        }
                        else {
                                sh '''
                                        echo Preparing Artifacts for ${COMPONENT}
                                        cd static
                                        zip -r ../${COMPONENT}-${TAG_NAME}.zip *                  
                                '''
                        }
                }
                
                stage('Upload Artifacts') {
                        withCredentials([usernamePassword(credentialsId: 'NEXUS', passwordVariable: 'NEXUS_PSW', usernameVariable: 'NEXUS_USR')]) {
                                sh "echo Uploading ${COMPONENT} Artifacts To Nexus"
                                sh "curl -f -v -u ${NEXUS_USR}:${NEXUS_PSW} --upload-file ${COMPONENT}-${TAG_NAME}.zip  http://172.31.93.234:8081/repository/${COMPONENT}/${COMPONENT}-${TAG_NAME}.zip || true"
                                sh "echo Uploading ${COMPONENT} Artifacts To Nexus is Completed"                   
                                
                        }                
                }       
        }
}

// def sonarChecks(){                            // related to shared library.
//        sh "echo Sonar Checks starts"
//        sh "echo Sonar checks done"
// }........