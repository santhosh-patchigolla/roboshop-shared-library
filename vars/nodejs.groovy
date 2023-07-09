def call () {
    node {
        common.lintChecks()
        env.ARGS="-Dsonar.sources=."
        common.sonarChecks()
    }
}


   // uncommited for doing due to scripted pipeline.
// def call (COMPONENT) {
//     pipeline {
//         agent { label 'work-station' }
//         environment {
//             SONARCRED = credentials('SONARCRED')
//             NEXUS = credentials('NEXUS')             // given the credential ovet the Jenkins pipeline
//             SONARURL = "172.31.89.102"
//             NEXUSURL = "172.31.93.234"
            
//         }
//         stages {

//             stage ('Lint Check') {                     
//                 steps {
//                     script {
//                         lintChecks()
//                     }
//                 }    
//             }

//             stage('Code Compile') {
//                 steps {
//                         sh "npm install"
//                 }
//             }

//             stage('Sonar Checks') {
//                 steps {
//                     script {  
//                         env.ARGS="-Dsonar.sources=."
//                         common.sonarChecks()
//                     }                                // calling the sonar checks function from line11
//                 }
//             }
//             stage('TestCases') {
//                 parallel {
//                     stage ('Unit Testing') {
//                         steps {
//                             sh "echo unit testing started"
//                             sh "echo unit testing done"
//                         }
//                     }
//                     stage ('Integration Testing') {
//                         steps {
//                             sh "echo Integration testing started"
//                             sh "echo Integration testing done"
//                         }
//                     }
//                     stage ('Funtional Testing') {
//                         steps {
//                             sh "echo Functional testing started"
//                             sh "echo Functional testing done"
//                         }
//                     }
//                 }
//             }

//             stage('Verify the artifact version') {
//                 when { expression { env.TAG_NAME != null } }        
//                 steps {
//                     script {
//                         env.UPLOAD_STATUS=sh (returnStdout: true, script: 'curl -L -s  http://${NEXUSURL}:8081/service/rest/repository/browse/${COMPONENT}/ | grep ${COMPONENT}-${TAG_NAME} || true' )    // true is used for the If the first statement is failed will treat as true
//                         print UPLOAD_STATUS                     
//                     }
//                 }
//             }

//             stage('Prepare Artifact') {
//                 when { 
//                     expression { env.TAG_NAME != null }             // when a tag is deducted then it will trigger
//                     expression { env.UPLOAD_STATUS == "" }                                    
//                 }      
//                 steps {
//                     sh '''
//                         echo prepare Artifacts ${COMPONENT}
//                         npm install
//                         zip ${COMPONENT}-${TAG_NAME}.zip node_modules server.js
                       
//                        '''  
//                 }
//             }

//             stage('Upload Artifacts') {
//                 when { 
//                     expression { env.TAG_NAME != null }
//                     expression { env.UPLOAD_STATUS == "" }                   
//                 }        
//                 steps {
//                     sh '''
//                         echo uploading artifacts to Nexus
//                         curl -f -u ${NEXUS_USR}:${NEXUS_PSW} --upload-file ${COMPONENT}-${TAG_NAME}.zip http://172.31.93.234:8081/repository/${COMPONENT}/${COMPONENT}-${TAG_NAME}.zip
//                         echo Uploading ${COMPONENT} Artifacts To Nexus is done

//                         '''  
//                 }    
//             }                                                                                          
//         }
//     }  
// }



// this is how you will pass the user name and password which is created in the Jenkins credential...NEXUS Is given name ID in the Jenkins and USR and PSW are the synatax to pass the credentials...








//                     sh "echo installing JSlint"
//                     sh "npm i jslint"
//                     sh "node_modules/jslint/bin/jslint.js server.js || true"   // this double pipe symbol includes that if the first command is failure make it as a true statement  (that pipe symbol makes forece fully passes the stage)


// def info(message) {
//     echo "hello I'm a function and my name is info"
//     echo "I'm printing the value of message is ${message}"
// }

// // calling the function
// info("hi")
