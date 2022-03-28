def SUBMITTER_USERNAME
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
            sh(returnStdout: true, script: '''#!/bin/bash
            docker ps -a --format \'{{.Names}}\' | grep -Eq "mysql-standalone"
            DBexists=$?
            if (( $DBexists == 0 ));
            then
            docker kill mysql-standalone
            else
             echo "Starting mysql conatiner"
            fi
            '''.stripIndent())
            sh "docker run --rm --name mysql-standalone -e MYSQL_ROOT_PASSWORD=admin -e MYSQL_DATABASE=HMS -e MYSQL_PASSWORD=admin -d mysql:8.0"
            }
        }
        stage('Run springboot container ') {
            steps {
                sh(returnStdout: true, script: '''#!/bin/bash
                docker ps -a --format \'{{.Names}}\' | grep -Eq "java-web-app"
                DBexists=$?
                if (( $DBexists == 0 ));
                then
                docker kill java-web-app
                else
                echo "Starting java-web-app conatiner"
                fi
                '''.stripIndent())
                sh "sleep 120"
                sh "docker run -p 8086:8086  --rm --name java-web-app --link mysql-standalone:mysql -d as6779/java-web-app:$BUILD_NUMBER"
            }
        }
        stage('Push Approval Notification') {
            options { timeout(time: 2, unit: 'MINUTES') }
            steps {
                script {
                    notifyEvents message: "Please <a href='${BUILD_URL}/input'>click here</a> to approve Docker Push : as6779/java-web-app:${BUILD_ID}", token: "iYINho7IsVUWIt9SFqQGBkWxpe5yD-A5"
                    emailext body: '$DEFAULT_CONTENT', subject: '$DEFAULT_SUBJECT', to: '$DEFAULT_RECIPIENTS'
                    SUBMITTER_USERNAME = input message: "Do you want approve to push as6779/java-web-app:${BUILD_ID} ?" , submitter: 'amit' , submitterParameter: 'Doneby
                }
            }
            post {
                sucess {
                            mail bcc: '', body: 'BUILD: ${BUILD_NUMBER}  is approved by ${SUBMITTER_USERNAME}', cc: '', from: '', replyTo: '', subject: 'BUILDi ${BUILD_NUMBER} APPROVED', to: 'amitshr6779@gmail.com'
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
    post {
        aborted {
            mail bcc: '', body: "BUILD Aborted Due to Timeout ", cc: '', from: '', replyTo: '', subject: 'BUILD ABORTED', to: 'amitshr6779@gmail.com'
        }
    }
}
