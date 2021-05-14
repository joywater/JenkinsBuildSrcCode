def call(targetPath){
    echo "******start delete folder******"

    if(isUnix()){

    }
    else{
        targetPath= targetPath.replace("/","\\")
        def cmd=String.format("if exist %s rmdir /s /q %s", targetPath, targetPath)
        bat cmd
    }
    
    echo "******end delete folder******"
}