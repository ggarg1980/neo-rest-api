pipeline {
  

agent any
stages {
      stage('Compile') {
        steps {
          withMaven(maven:'maven_3_5_0')
          {
            sh 'mvn clean compile'
          }
        }
    }

    stage('Build') {
        steps {
            sh 'echo "Hello World ...... "'
            echo "PATH = ${PATH}"
            sh '''
                echo "Multiline shell steps works too"
                ls -lah
                mvn clean package
            '''
        }
    }
}
}
