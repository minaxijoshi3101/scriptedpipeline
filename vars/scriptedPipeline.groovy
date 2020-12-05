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
      rm -rf $REPO_NAME
      git clone $REPO_NAME
      cd $REPO_NAME
      git checkout $BRANCH
      '''
  }
  stage("build")
  {
  sh '''
    cd $REPO_NAME
    mvn clean install
  '''
  }
}
}
}
