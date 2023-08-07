def call() {
    properties([
            parameters([
                choice(choices: 'dev\nprod', description: "Select environment", name: "ENV"),
                choice(choices: 'apply\ndestroy', description: "Chose the action", name: "ACTION")
            ]),
        ])
    node {
        ansiColor('xterm') {
            git branch: 'main', url: "https://github.com/santhosh-patchigolla/${REPONAME}.git"
            
            stage('terraform init') {
                sh "cd ${TFDIR}"
                sh "terrafile -f env-${ENV}/Terrafile"
                sh "terraform init -backend-config=env-${ENV}/${ENV}-backend.tfvars"            
            }

            stage('terraform plan') {
                sh "cd ${TFDIR}"
                sh "terraform plan -var-file=env-${ENV}/${ENV}.tfvars"
            }

            stage('Terraform Action') {
                sh "cd ${TFDIR}"
                sh "terraform ${ACTION} -auto-approve -var-file=env-${ENV}/${ENV}.tfvars"
                }
            }
        }
    }


