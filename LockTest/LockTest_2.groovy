node
{
    stage('Echo')
    {
		lock(resource: 'ClearProject', inversePrecedence: true)
		{
            echo 'I am free.'
        }
    }
}