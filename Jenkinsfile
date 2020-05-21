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
	
stage ('Package as Image') {
      steps {
        withCredentials([usernamePassword(
            credentialsId: "docker-hub",
            usernameVariable: "Username",
            passwordVariable: "Password"
        )]) {
        dockerbuild('cloudmonster123', 'spring-boot-app-k8s-deployment')
        }
      }
    }
    stage('Deploy to K8s'){
	  steps{
		  sh "chmod +x changeTag.sh"
		  sh "./changeTag.sh v$BUILD_NUMBER"
	     sshagent(['Kops-machine']){
		     sh "scp -o StrictHostKeyChecking=no spring-app-deploy.yml service.yaml ec2-user@13.58.215.7:/home/ec2-user"
			 script{
			     try{
				    sh "ssh ec2-user@13.58.215.7 kubectl apply -f ."
				    sh "ssh ec2-user@13.58.215.7 kubectl get po"
				    sh "ssh ec2-user@13.58.215.7 kubectl get svc"
				     
                             }catch(error){
				    sh "ssh ec2-user@13.58.215.7 kubectl create -f ."
                             }
	   }
         }
	}
    }
	
    }
}
