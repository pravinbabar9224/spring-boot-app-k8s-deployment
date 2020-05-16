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
	  
   stage('Compile and SonarQube Analysis') {
    steps {
        withSonarQubeEnv('SonarQube-Server') {
            sh 'mvn clean install sonar:sonar'
        }
        timeout(time: 10, unit: 'MINUTES') {
            waitForQualityGate abortPipeline: true
        }
      }
   }
  /*stage('Test') {
      steps {
	      script{
		      sh "mvn spring-boot:run -Drun.jvmArguments='-Dserver.port=8081'"
      }
    }
  }*/
	
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
    stage('Deploy to K8s'){
	  steps{
	     sshagent(['Kops-machine']){
		     sh "scp -o StrictHostKeyChecking=no sample.yaml ec2-user@13.58.215.7:/home/ec2-user"
			 script{
			     try{
				    sh "ssh ec2-user@13.58.215.7 kubectl apply -f ."
                             }catch(error){
				    sh "ssh ec2-user@13.58.215.7 kubectl create -f ."
                             }
	   }
         }
	}
    }
  }
}
