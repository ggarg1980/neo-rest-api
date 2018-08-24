pipeline {
  

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
                mvn clean package
            '''
        }
    }
}
}
