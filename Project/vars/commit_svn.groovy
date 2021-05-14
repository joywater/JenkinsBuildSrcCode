def call(String svnPath, String log){
    echo "******start commit svn******"
    if(isUnix()){

    }
    else{
        bat String.format("%s Commit %s %s", environment.Path_Svn, svnPath, log)
    }
    
    echo "******end commit svn******"
}