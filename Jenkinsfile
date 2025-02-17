pipeline {
    agent any  // 모든 Jenkins 에이전트에서 실행
    environment {
        AWS_REGION = 'ap-northeast-2'
        ECR_REPO_BACKEND = '605134473022.dkr.ecr.ap-northeast-2.amazonaws.com/olive-back'  // 백엔드 ECR URL
        // ECR_REPO_FRONTEND = '605134473022.dkr.ecr.ap-northeast-2.amazonaws.com/olive-front'  // 프론트엔드 ECR URL
        IMAGE_TAG = "latest"
    }
    stages {
        stage('Checkout') {
            steps {
                // GitHub에서 코드 체크아웃
                checkout scm
            }
        }
        stage('Build Backend') {
            steps {
                script {
                    // 백엔드 Gradle 빌드
                    echo "Building Backend"
                    sh './gradlew clean build'  // Gradle로 백엔드 빌드
                    sh 'docker build -t $ECR_REPO_BACKEND:$IMAGE_TAG .'  // 백엔드 Docker 이미지 빌드
                }
            }
        }
        // stage('Build Frontend') {
        //     steps {
        //         script {
        //             // 프론트엔드 React 앱 빌드
        //             echo "Building Frontend"
        //             sh 'npm install'  // React 앱 의존성 설치
        //             sh 'npm run build'  // React 앱 빌드
        //             sh 'docker build -t $ECR_REPO_FRONTEND:$IMAGE_TAG .'  // 프론트엔드 Docker 이미지 빌드
        //         }
        //     }
        // }
        stage('Run Tests') {
            steps {
                script {
                    // 백엔드 JUnit 테스트 실행
                    echo "Running Backend Tests"
                    sh './gradlew test'  // 백엔드 JUnit 테스트 실행
                    // // 프론트엔드 테스트 실행 (예: Jest 사용)
                    // echo "Running Frontend Tests"
                    // sh 'npm test'  // React 앱 테스트 실행
                }
            }
        }
        stage('Push Docker Images to ECR') {
            steps {
                script {
                    // Docker 이미지를 ECR에 푸시
                    echo "Pushing Docker images to ECR"
                    sh 'aws ecr get-login-password --region $AWS_REGION | docker login --username AWS --password-stdin $ECR_REPO_BACKEND'
                    sh 'docker push $ECR_REPO_BACKEND:$IMAGE_TAG'  // 백엔드 이미지 푸시
                    // sh 'aws ecr get-login-password --region $AWS_REGION | docker login --username AWS --password-stdin $ECR_REPO_FRONTEND'
                    // sh 'docker push $ECR_REPO_FRONTEND:$IMAGE_TAG'  // 프론트엔드 이미지 푸시
                }
            }
        }
        stage('Deploy to Kubernetes (EKS)') {
            steps {
                script {
                    // EKS에 배포 (kubectl 사용)
                    echo "Deploying to Kubernetes (EKS)"
                    sh 'kubectl apply -f k8s/backend-deployment.yaml'
                    sh 'kubectl apply -f k8s/backend-service.yaml'
                    // sh 'kubectl apply -f k8s/frontend-deployment.yaml'
                    // sh 'kubectl apply -f k8s/frontend-service.yaml'
                }
            }
        }
    }
    post {
        always {
            echo 'Pipeline finished.'
        }
        success {
            echo 'Pipeline executed successfully.'
        }
        failure {
            echo 'Pipeline failed.'
        }
    }
}
