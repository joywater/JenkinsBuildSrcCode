library 'Common'
def call(String platform,String speedup_branch){
    echo "******start refresh cache server " + platform + "******"  
    
    close_library()
    def targetLib=String.format("%s%s/%s", environment.Path_LibraryCache,speedup_branch,platform)
    link_library(targetLib)

	def powershell="PowerShell.exe (Get-Item C:/qyg/Game301/Game301_Client_NewDesign/Src/Game301/Library).Target"
    bat powershell
	
    def logFile=String.format("%slog_refresh_cache_%s_%s.log", environment.Path_Log, platform, BUILD_ID)
	def cmd=String.format("%s -batchmode -quit -nographics -projectPath %s -buildTarget %s -logFile %s", environment.Path_Unity, environment.Path_Project, platform, logFile)
    bat cmd
    echo "******end refresh cache server " + platform + "******"    
}