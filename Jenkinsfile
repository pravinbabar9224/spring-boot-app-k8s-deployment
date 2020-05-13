pipeline {
  environment {
    registry = "cloudmonster123/docker"
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
         git 'https://github.com/pravinbabar9224/k8s-deployment.git'
      }
    }
	
    stage('build & SonarQube Scan') {
    withSonarQubeEnv('SonarQube-Server') {
      sh 'mvn clean install sonar:sonar'
      } 
     }
    
 
    stage("Quality Gate") {
    timeout(time: 1, unit: 'HOURS') { // Just in case something goes wrong, pipeline will be killed after a timeout
    def qg = waitForQualityGate() // Reuse taskId previously collected by withSonarQubeEnv
    if (qg.status != 'OK') {
      error "Pipeline aborted due to quality gate failure: ${qg.status}"
       }
     }
    }
	
    stage('Package as Image') {
      steps{
        script {

          dir('/var/lib/jenkins/workspace/kuberntes-cicd/src/'){
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
