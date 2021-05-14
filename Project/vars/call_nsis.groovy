library 'Common'
def call(){
    echo "******start call nsis******"

    def cmdFmt = "%s /V4 %s"
    bat String.format(cmdFmt, environment.Path_Nsis, environment.Path_NsisScript) 
    
    echo "******end call nsis******"
}