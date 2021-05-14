def call(){
    echo "******start init smb******"

    if(isUnix()){
        
    }
    else{        
        bat "net use * /delete /y"
        bat "net use Z: \\\\10.2.48.64\\Data\\Game301 \"qyg301\" /user:\"qyg\""
    }

    echo "******end init smb******"
}