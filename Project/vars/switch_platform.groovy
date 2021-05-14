library 'Common'
def call(String platform){
    echo "******start switch platform******"   
    
    if(isUnix()){

    }
    else{
		def powershell="PowerShell.exe (Get-Item C:/qyg/Game301/Game301_Client_NewDesign/Src/Game301/Library).Target"
        bat powershell
		
        def cmd=String.format("%s -batchmode -quit -nographics -projectPath %s -buildTarget %s -logFile %slog_switch_platform_%s_%s.log", environment.Path_Unity, environment.Path_Project, platform, environment.Path_Log, platform, BUILD_ID)
        bat cmd
    }
    
    echo "******end switch platform******"
}