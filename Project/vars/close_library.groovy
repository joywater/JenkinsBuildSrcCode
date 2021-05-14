def call(){
    echo "******start close library******"
    
    if(isUnix()){

    }
    else{        
        def target= String.format("\"%sLibrary\"", environment.Path_Project)        
        bat "if exist " + target + " rmdir /s /q " + target
    }
    
    echo "******end close library******"
}