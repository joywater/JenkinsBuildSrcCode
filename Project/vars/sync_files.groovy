def call(String srcPath, String targetPath, ArrayList excludes, boolean delete=true, boolean isRemote=false){
    echo "******start sync files******"

    def excludeStr =""
    for(e in excludes){
        excludeStr+= String.format("--exclude=%s ", e)
    }

    if(isUnix()){

    }
    else{
        dir(environment.Path_Rsync_Bin){
            if(!isRemote){
                def cmd=delete? String.format("rsync -a --delete %s/cygdrive/%s /cygdrive/%s", excludeStr, srcPath.replace(":/","/"), targetPath.replace(":/","/")):
                            String.format("rsync -a %s/cygdrive/%s /cygdrive/%s", excludeStr, srcPath.replace(":/","/"), targetPath.replace(":/","/"))
                bat cmd
            }
            else{
                def cmd=delete? String.format("rsync -az --delete %s/cygdrive/%s %s", excludeStr, srcPath.replace(":/","/"), targetPath):
                            String.format("rsync -az %s/cygdrive/%s %s", excludeStr, srcPath.replace(":/","/"), targetPath)
                bat cmd
            }
           
        }
    }
    
    echo "******end sync files******"
}