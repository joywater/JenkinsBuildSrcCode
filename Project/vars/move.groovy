def call(String srcPath, String targetPath){
    echo "******start cut folder******"

    if(isUnix()){

    }
    else{
        def cmd=String.format("move /y %s %s", srcPath.replace("/","\\"), targetPath.replace("/","\\"))
        bat cmd
    }
    
    echo "******end cut folder******"
}