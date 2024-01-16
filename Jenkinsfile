pipeline {
    agent {
        docker { image 'gradle:jdk17' }
    }
    stages {
        stage('Build') {
            steps {
                sh './gradlew build'
            }
            post {
                always {
                    junit 'build/test-results/test/*.xml'
                }
                success {
                    recordCoverage qualityGates: [[criticality: 'FAILURE', metric: 'LINE', threshold: 75.0]], tools: [[pattern: 'build/reports/jacoco/test/jacocoTestReport.xml']]
                }
            }
        }
    }
}