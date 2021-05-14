def call(){
    echo "******start kill unity processes******"
    if(isUnix()){

    }
    else{
        bat "START /wait taskkill /f /IM Unity.exe"
        bat "START /wait taskkill /f /IM devenv.exe"
    }
    
    echo "******end kill unity processes******"
}