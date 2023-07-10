def call () {
    node {
        git branch: 'main', url: "https://github.com/santhosh-patchigolla/${COMPONENT}.git"
        env.APP_TYPE="angularjs"
        common.lintChecks()
        env.ARGS="-Dsonar.sources=."  
        common.sonarChecks() 
        common.testCases() 
        if(env.TAG_NAME != null) {
            common.artifacts()
        }            
    }
}




// def lintChecks(){
//     sh '''
//         echo Installing AngularLint for ${COMPONENT}
//         echo Installing lint checks done for the ${COMPONENT}

//     '''
// }


// def call (COMPONENT) {
//     pipeline {
//         agent { label 'work-station' }
//         environment {
//             SONARCRED = credentials('SONARCRED') 
//             SONATURL  = "172.31.89.102"
//         }        
//         stages {

//             stage ('Lint Check') {                     
//                 steps {
//                     script {
//                         lintChecks()
//                     }
//                 }    
//             }

//             stage('Code Quality') {
//                 steps {
//                         script {
//                             env.ARGS="-Dsonar.sources=."
//                             common.sonarChecks()
//                         }
//                 }
//             }                                              

//         }
//     }
// }


