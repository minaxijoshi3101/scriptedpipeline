def call(Map pipelineparam)
{
env.REPO_NAME = pipelineparam.REPO_NAME
env.BRANCH = pipelineparam.BRANCH
pipeline
{
node
{
  stage("checkout scm")
  {
    sh '''
      rm -rf CounterWebApp
      git clone $REPO_NAME
      cd CounterWebApp
      git checkout $BRANCH
      '''
  }
  stage("build")
  {
  sh '''
    cd CounterWebApp
    mvn clean install
  '''
  }
}
}
}
