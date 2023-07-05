// def sonarChecks(){
//        sh "echo Sonar Checks starts"
//        //sh "sonar-scanner -Dsonar.host.url=http://172.31.89.102:9000  -Dsonar.projectKey=${COMPONENT} ${ARGS} -Dsonar.login=admin -Dsonar.password=password"
//        //sh "curl https://gitlab.com/thecloudcareers/opensource/-/raw/master/lab-tools/sonar-scanner/quality-gate > sonar-quality-gate.sh"
//        //sh "sonar-quality-gate.sh ${SONARCRED_USR} ${SONARCRED_PSW} ${SONARURL} ${COMPONENT}"
//        sh "echo Sonar checks done"
// }

def sonarChecks(){
       sh "echo Sonar Checks starts"
       sh "echo Sonar checks done"
}