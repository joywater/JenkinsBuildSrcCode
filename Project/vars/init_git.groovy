def call(String gitPath, String branch, boolean fetchBranch=true, boolean pullBranch=true){
    echo "******start init git******"
    if(isUnix()){

    }
    else{        
        dir(gitPath){
            bat "rm -f ./.git/index.lock"
            bat "git reset --hard"
            bat "git clean -df"
            if(branch != ""){
                if(fetchBranch){
                    bat "git fetch -p"
                }                
                bat "git checkout "+branch
                if(pullBranch){
                    bat "git reset --hard"
                    bat "git clean -df"
                    bat "git pull --rebase origin "+branch
                }                
            }            
        }
    }
    
    echo "******end init git******"
}