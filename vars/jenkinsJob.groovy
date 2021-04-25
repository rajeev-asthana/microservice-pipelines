def call(){
    node {
        stage('Checkout') {
            checkout scm
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
    
     stage("Package artifact"){
           sh '''
              export MAVEN_HOME=/var/jenkins_home/apache-maven/apache-maven-3.8.1
              export PATH=$PATH:$MAVEN_HOME/bin
              mvn --version
              #mvn clean package
              mvn package
              '''
        }
}

def buildAndTest(){
    
    stage("Backend tests"){
           sh '''
              export MAVEN_HOME=/var/jenkins_home/apache-maven/apache-maven-3.8.1
              export PATH=$PATH:$MAVEN_HOME/bin
              mvn --version
              #mvn clean test package
              mvn test
              '''
        }
}
