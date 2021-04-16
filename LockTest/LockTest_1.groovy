node
{
  stage('Input')
  {
    lock(resource: 'ClearProject', inversePrecedence: false) 
    {
      echo 'Do something here that requires unique access to the resource'
      def answer = input message:'是否放行',parameters:[booleanParam(defaultValue:true,description:'是',name:'isGo')]
      echo "answer " + answer
    }

    lock(label: 'AAAA')
    {
      echo 'Do something here that requires unique access to the resource'
      def answer = input message:'是否放行',parameters:[booleanParam(defaultValue:true,description:'是',name:'isGo')]
      echo "answer " + answer
    }
  }
}