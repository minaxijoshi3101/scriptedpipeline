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
    mvn sonar:sonar
    //we refer sonar roperties file, wherein project key is mentioned, sonar runner should be present on our build server.
    '''
  }
  stage("build and upload artifacts to ECR")
  {
      //we use mvn deploy for docker image creation using docker mvn plugin which created=s custom image of an app and pushes that image to docker registry
  sh '''
    cd etoedevops
    /opt/maven/bin/mvn deploy -P docker -Ddocker.host=${DOCKER_HOST} -Ddocker.registry.name=${DOCKER_REGISTRY} -Dmaven.test.skip=true
  '''
  }
}
}
}
