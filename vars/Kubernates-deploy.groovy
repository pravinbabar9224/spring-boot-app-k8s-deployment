def call() {
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
