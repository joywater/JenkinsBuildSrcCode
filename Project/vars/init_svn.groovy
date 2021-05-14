def call(String svnPath){
    echo "******start init svn******"
    if(isUnix()){

    }
    else{
        dir(svnPath){
            bat "svn cleanup"
            bat "svn revert -R ."
            bat "svn update"
        }
    }
    
    echo "******end init svn******"
}