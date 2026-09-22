pipeline {
    agent any

    tools {
        maven 'Maven-3.9'
        jdk 'JDK-11'
    }

    environment {
        REPORT_PATH = 'reports/TestReport.html'
    }

    stages {

        stage('Checkout') {
            steps {
                echo 'Cloning repository...'
                checkout scm
            }
        }

        stage('Build') {
            steps {
                echo 'Compiling source code...'
                sh 'mvn clean compile -q'
            }
        }

        stage('Run Tests') {
            steps {
                echo 'Running automation test suite...'
                sh '''
                    mvn test \
                    -Dheadless=true \
                    -Dbrowser=chrome \
                    -Dsurefire.failIfNoSpecifiedTests=false
                '''
            }
            post {
                always {
                    echo 'Publishing TestNG results...'
                    publishHTML(target: [
                        allowMissing: false,
                        alwaysLinkToLastBuild: true,
                        keepAll: true,
                        reportDir: 'reports',
                        reportFiles: 'TestReport.html',
                        reportName: 'Automation Test Report'
                    ])
                    // Archive screenshots
                    archiveArtifacts artifacts: 'reports/screenshots/**', allowEmptyArchive: true
                }
            }
        }

        stage('Publish Results') {
            steps {
                echo "Test run complete. Report available at: ${REPORT_PATH}"
            }
        }
    }

    post {
        success {
            echo '✅ Pipeline completed successfully'
        }
        failure {
            echo '❌ Pipeline failed — check test report for details'
        }
    }
}
