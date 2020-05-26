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
   
	
  stage('Sonar Analysis') {
    steps {
	 sonarscan()
        }
      }  
       
   
  stage('Unit Testing') {
      steps {
	      unittest()
    }
  }
	
stage ('Package as Image') {
      steps {
        withCredentials([usernamePassword(
            credentialsId: "docker-hub",
            usernameVariable: "Username",
            passwordVariable: "Password"
        )]) {
        dockerbuild('cloudmonster123', 'spring-boot-app-k8s_deployment-new')
        }
      }
    }
stage('Commit to CD Repo'){
	  steps{
            codecodecheckout(branch: 'master', scmUrl: 'https://github.com/pravinbabar9224/spring-boot-app-k8s-deployment-CD.git'){
		    sh "chmod +x changeTag.sh"
		    sh "./changeTag.sh v$BUILD_NUMBER"
			sh "git status"
			sh "git add *"
			sh "git commit 'new commit'"
			sh "git remote add origin https://github.com/pravinbabar9224/spring-boot-app-k8s-deployment-CD.git"
			sh "git push origin master -u cloudmonster123 -p Aai@123@Pappa"
	      
	}
    }
	
    }
	}
    }
	
    }
}
