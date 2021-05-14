def call(String svnPath){
    def revision=""
    if(isUnix()){

    }
    else{
        
        dir(svnPath){
            def std = bat([
                            returnStdout: true, 
                            script: 'svn info --show-item last-changed-revision'
                        ])
            revision= std.tokenize().last()
        }
    }
    return revision
}