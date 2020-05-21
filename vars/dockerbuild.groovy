def call(String hubuser, String repo) {
    withCredentials([usernamePassword(
            credentialsId: "Dockerhub",
            usernameVariable: "Username",
            passwordVariable: "Password"
    )]) {
        sh "docker login -u '$Username' -p '$Password'"
    }
    sh "docker image build -t ${hubuser}/${repo}:v${env.BUILD_NUMBER} .  --no-cache"
    sh "docker image push ${hubuser}/${repo}:v${env.BUILD_NUMBER}"
    sh "docker image rm ${hubuser}/${repo}:v${env.BUILD_NUMBER}"   
}
