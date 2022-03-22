pipeline {
    agent any
    stages {
        stage('Docker Build Started') {
            steps {
                script {
                    notifyEvents message: "Docker Build Started", token: "iYINho7IsVUWIt9SFqQGBkWxpe5yD-A5"
                }
            }
        }
        stage('Docker build') {
            steps {
           sh "docker build -t as6779/java-web-app:latest -t  as6779/java-web-app:$BUILD_NUMBER . "
            }
        }
        stage('Run Mysql Conatiner'){
            steps {
                sh "docker run --rm --name mysql-standalone -e MYSQL_ROOT_PASSWORD=admin -e MYSQL_DATABASE=HMS -e MYSQL_PASSWORD=admin -d mysql:8.0"
            }
        }
        stage('Run springboot container ') {
            steps {
                sh "sleep 120"
                sh "docker run -p 8086:8086  --rm --name java-web-app --link mysql-standalone:mysql -d as6779/java-web-app:$BUILD_NUMBER"
            }
        }
        stage('Push Approval Notification') {
            options { timeout(time: 2, unit: 'MINUTES') }
            steps {
                script {
                    notifyEvents message: "Please <a href='${BUILD_URL}/input'>click here</a> to approve Docker Push : as6779/java-web-app:${BUILD_ID}", token: "iYINho7IsVUWIt9SFqQGBkWxpe5yD-A5"
                    userInput = input submitter: '', message: "Do you approve to push as6779/java-web-app:${BUILD_ID} ?"
                }
            }
        }
        stage('Push Docker Image') {
            steps {
            withCredentials([string(credentialsId: 'docker-pwd', variable: 'dockerHubPwd')]) {
            sh "docker login -u as6779 -p ${dockerHubPwd}"
            }
             sh 'docker push as6779/java-web-app:$BUILD_NUMBER'
            }
        }
        stage('Push Docker Image to Docker-Hub') {
            steps {
                script {
                    notifyEvents message: "Sucessfully Pushed Docker Image to Docker Hub", token: "iYINho7IsVUWIt9SFqQGBkWxpe5yD-A5"
                }
            }
        }
    }
}
