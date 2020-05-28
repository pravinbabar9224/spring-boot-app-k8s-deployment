@Library('jenkins-shared-library') _

def loadValuesYaml(){
def valuesYaml = readYaml (file: 'input.yaml')
return valuesYaml;
}

pipeline {
  
agent any
  stages {
        stage ('deployment'){ 
               steps { 
                     script {

                          valuesYaml = loadValuesYaml()
}
 }
}
    stage('Cleaning Workspace') {
        steps {
          deleteDir()
        }
    }
    stage('GitHub Checkout') {
      steps {
	      echo valuesYaml.Gitdetails.branch
         codecodecheckout(branch: valuesYaml.Gitdetails.branch, scmUrl: valuesYaml.Gitdetails.repo)
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
            credentialsId:valuesYaml.CredId.dockercred,
            usernameVariable: "Username",
            passwordVariable: "Password"
        )]) {
        dockerbuild(valuesYaml.Dockerdetails.dockeruser,valuesYaml.Dockerdetails.dockerrepo)
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
/*stage('Commit to CD Repo'){
	  steps{                   
		    sh "chmod +x changeTag.sh"
		    sh "./changeTag.sh v$BUILD_NUMBER"
		    sh "git clone https://github.com/pravinbabar9224/spring-boot-app-k8s-deployment-CD.git"
		    sh "cp sample1.yaml spring-boot-app-k8s-deployment-CD/manifest/"
			sh "cd spring-boot-app-k8s-deployment-CD/manifest/"
			sh "git add sample1.yaml"
			sh "git commit -m 'new commit'"
		  withCredentials([usernamePassword(
                        credentialsId: "git-new",
                      usernameVariable: "Username",
                      passwordVariable: "Password"
                      )]) {
                        sh "git push origin master"
                       }
			
	      
	}
    }*/
	
    }
  }
