library "CommonLibrary"

node 
{
	stage("UpdateProject") 
	{
		echo ProjectConfig.ProjectName;
		// GitUtil.CloneProject(GlobalConfig.ProjectPath, GlobalConfig.ProjectUrl, "main");
	}
}