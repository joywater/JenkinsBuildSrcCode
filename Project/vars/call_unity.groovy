library 'Common'
def call(String platform, String method, String params="", String logName){
    echo "******start call unity******"

    if(isUnix()){
        
    }
    else{
		def powershell="PowerShell.exe (Get-Item C:/qyg/Game301/Game301_Client_NewDesign/Src/Game301/Library).Target"
        bat powershell
		
        def logFile=String.format("%slog_%s_%s_%s.log", environment.Path_Log, logName, platform, BUILD_ID)
        def cmdFmt="%s -batchmode -quit -nographics -projectPath %s -buildTarget %s -executeMethod %s -logFile %s %s"
        bat String.format(cmdFmt, environment.Path_Unity, environment.Path_Project, platform, method, logFile, params)
    }

    echo "******end call unity******"
}