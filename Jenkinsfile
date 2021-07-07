pipeline {
    agent any

    parameters {
         string(
              defaultValue: '',
              name: 'TAG_NAME',
              trim: true
         )
    }

    tools {
        jdk 'jdk-8'
    }

    environment {
        MAVEN_USER = credentials("ossrh_user")
        MAVEN_PASSWORD = credentials("ossrh_password")
        SIGNING_PASSWORD = credentials("signing_password")
    }

    stages {

        stage('Build') {
            steps {
                 sh  "chmod +x scripts/download_libs.sh"
                 sh  "./scripts/download_libs.sh  ${TAG_NAME}"
                 sh  './gradlew build -Psigning.password=${SIGNING_PASSWORD} --stacktrace'
            }
        }

        stage('Publish') {
            steps {
                sh  "chmod +x scripts/download_libs.sh"
                sh  "./scripts/download_libs.sh  ${TAG_NAME}"
                sh  './gradlew publish -Psigning.password=${SIGNING_PASSWORD}  --no-daemon --no-parallel --stacktrace'
            }
         }

        stage('Results') {
            steps {
                archiveArtifacts 'build/libs/*.jar'
            }
        }
    }
}
