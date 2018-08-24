pipeline {
    
tools
{
    maven 'maven 3'
    jdk 'java 8'
}
 
agent any
stages {
    stage('Build') {
        steps {
            sh 'echo "Hello World"'
            echo "PATH = ${PATH}"
            echo "M2_HOME = ${M2_HOME}"
            sh '''
                echo "Multiline shell steps works too"
                ls -lah
            '''
        }
    }
}
}
