def sonarChecks(){
    sh '''
        echo Sonar Checks starts
        sonar-scanner -Dsonar.host.url=http://172.31.89.102:9000  -Dsonar.projectKey=${COMPONENT} ${ARGS} -Dsonar.login=admin -Dsonar.password=password
        curl https://gitlab.com/thecloudcareers/opensource/-/raw/master/lab-tools/sonar-scanner/quality-gate > sonar-quality-gate.sh
        sonar-quality-gate.sh ${SONARCRED_USR} ${SONARCRED_PSW} ${SONATURL} ${COMPONENT}
        echo Sonar checks done
    '''
}