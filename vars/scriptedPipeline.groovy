def call(Map pipelineparam)
{
env.REPO_NAME = pipelineparam.REPO_NAME
env.BRANCH = pipelineparam.BRANCH
env.DOCKER_HOST = pipelineparam.DOCKER_HOST
env.DOCKER_REGISTRY=pipelineparam.DOCKER_REGISTRY
  
pipeline
{
node
{
  stage("checkout scm")
  {
    sh '''
      rm -rf etoedevops
      git clone $REPO_NAME
      cd etoedevops
      git checkout $BRANCH
      '''
  }
  stage("static code analysis")
  {
    sh '''
    
    '''
  }
  stage("build and upload artifacts to ECR")
  {
  sh '''
    cd etoedevops
    /opt/maven/bin/mvn deploy -P docker -Ddocker.host=${DOCKER_HOST} -Ddocker.registry.name=${DOCKER_REGISTRY} -Dmaven.test.skip=true
  '''
  }
}
}
}
