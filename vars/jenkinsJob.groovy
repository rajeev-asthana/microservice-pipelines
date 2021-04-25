def call(){
    node {
        stage('Checkout') {
            checkout scm
        }
        
        stage('Maven Environment Setup'){
           sh '''
              export MAVEN_HOME=/var/jenkins_home/apache-maven/apache-maven-3.8.1
              export PATH=$PATH:$MAVEN_HOME/bin
              mvn --version
              mvn clean package
              '''
        }

        // Execute different stages depending on the job
        if(env.JOB_NAME.contains("deploy")){
            packageArtifact()
        } else if(env.JOB_NAME.contains("test")) {
            buildAndTest()
        }
    }
}

def packageArtifact(){
    stage("Package artifact") {
        sh "mvn package"
    }
}

def buildAndTest(){
    stage("Backend tests"){
        sh "mvn test"
    }
}
