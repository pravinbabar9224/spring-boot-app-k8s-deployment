@Library('jenkins-shared-library') _

pipeline {
  
agent any
  stages {
    stage('Cleaning Workspace') {
        steps {
          deleteDir()
        }
    }
    stage('GitHub Checkout') {
      steps {
         codecodecheckout(branch: 'master', scmUrl: 'https://github.com/pravinbabar9224/spring-boot-app-k8s-deployment.git')
      }
    }
	  
   stage('Compile') {
    steps {
        mybuild()
        }
        }
   
	
  /* stage('Sonar Analysis') {
    steps {
        sonarscan()
        }
        }
   */
  stage('Unit Testing') {
      steps {
	      unittest()
    }
  }
	
    stage('Package as Image') {
      steps{
        steps {
        dockerbuild('cloudmonster123', 'spring-boot-app-k8s-deployment')
         }  
        }
      }
    }
}
