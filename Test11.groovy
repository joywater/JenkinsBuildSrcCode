lock(resource: 'ClearProject', inversePrecedence: true) {
    node {
        stage('Echo') {
            echo 'I am free.'
        }
    }
}