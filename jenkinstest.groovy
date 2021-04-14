library "CommonLibrary"

node 
{
	stage("CloneProject") 
	{
		GitUtil.CloneProject(GlobalConfig.ProjectPath, GlobalConfig.ProjectUrl, "main");
	}
}