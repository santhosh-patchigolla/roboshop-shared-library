def call () {
    node {
        git branch: 'main', url: "https://github.com/santhosh-patchigolla/${COMPONENT}.git"
        env.APP_TYPE="python"
        common.lintChecks()
        env.ARGS="-Dsonar.sources=."
        common.sonarChecks()
        common.testCases()
        if(env.TAG_NAME != null) {
            common.artifacts()
        }        
    }
}

        

// def lintChecks(){                // un comment the below lines
//     sh '''
//         echo Installing PYLint for ${COMPONENT}
//         pip install pylint
//         echo Installing lint checks done for the ${COMPONENT}

//     '''
// }



// def call (COMPONENT) {
//     pipeline {
//         agent { label 'work-station' }
//         environment {  
//             SONARCRED = credentials('SONARCRED')
//             SONATURL = "172.31.89.102"
//         }        
//         stages {
//             stage ('Lint Check') {                     
//                 steps {
//                     script {
//                         lintChecks()
//                     }
//                 }    
//             }

//             stage('Sonar Checks') {
//                 steps {
//                     script {
//                         env.ARGS="-Dsonar.sources=."
//                         common.sonarChecks()
//                     }
//                 }
//             }

//             stage('Testing') {
//                 steps {
//                         sh "echo Testing"
//                 }
//             }                                                          

//         }
//     }
// }










//                     sh "echo installing JSlint"
//                     sh "npm i jslint"
//                     sh "node_modules/jslint/bin/jslint.js server.js || true"   // this double pipe symbol includes that if the first command is failure make it as a true statement  (that pipe symbol makes forece fully passes the stage)


// def info(message) {
//     echo "hello I'm a function and my name is info"
//     echo "I'm printing the value of message is ${message}"
// }

// // calling the function
// info("hi")
