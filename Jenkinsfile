pipeline {
  environment {
    registry = "cloudmonster123/spring-boot-app-k8s-deployment"
    registryCredential = 'docker-hub'
    dockerImage = ''
  }
  agent any
  stages {
    stage('Cleaning Workspace') {
        steps {
          deleteDir()
        }
    }
    stage('GitHub Checkout') {
      steps {
         git 'https://github.com/pravinbabar9224/spring-boot-app-k8s-deployment.git'
      }
    }
	
    stage('Package as Image') {
      steps{
        script {

          dir('/root/.jenkins/workspace/spring-boot-app-k8s-deployment'){
             dockerImage = docker.build registry + ":v$BUILD_NUMBER"
         }
        }
      }
    }
    stage('Push Image to Docker Registry') {
      steps{
        script {
          dir('/var/lib/jenkins/workspace/kuberntes-cicd/src/'){
               docker.withRegistry( '', registryCredential ) {
                   dockerImage.push()
          }
         }
        }  
      }
    }
  }
}
