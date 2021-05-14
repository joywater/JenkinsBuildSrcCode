def call(String gitPath, String branch, String msg, String folderPath="", boolean pushBranch=true){
    echo "******start commit git******"
    if(isUnix()){

    }
    else{        
        dir(gitPath){
            if(folderPath==""){
                bat "git add ."
                bat String.format("git commit --allow-empty -am \"%s\"", msg)
            }
            else{
                bat String.format("git add %s", folderPath)
                bat String.format("git commit --allow-empty -m \"%s\" %s", msg, folderPath)
            }

            if(pushBranch){
                bat "git reset --hard"
                bat "git clean -fd"
                bat String.format("git pull --rebase origin %s", branch)
                bat String.format("git push origin %s", branch)
            }
        }
    }
    
    echo "******end commit git******"
}