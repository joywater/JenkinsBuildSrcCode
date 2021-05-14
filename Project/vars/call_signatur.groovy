library 'Common'
def call(String signFileName){
    echo "******start call signtool******"

    def cmdFmt = "%s sign /f %s /t %s /d  '%s'  %s"
    def signName = "Gyee签名"
    bat String.format(cmdFmt, environment.Path_Signature, environment.Path_Signature_pfx, environment.Path_timstamp_dll, signName, signFileName)
    
    echo "******end call signtool******"
}