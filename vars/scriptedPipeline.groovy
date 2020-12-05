def call(Map pipelineparam)
{
pipeline
{
node
{
  stage("checkout scm")
  {
    sh '''
      git clone $REPO_NAME
      git checkout $BRANCH
      '''
  }
  stage("build")
  {
  sh '''
    mvn clean install
  '''
  }
}
}
}
