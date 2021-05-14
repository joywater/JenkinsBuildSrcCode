library 'Common'
node(node_name){
        stage('prepare'){
                close_unity()
                init_git(environment.Path_Root, branch)
                //init_git(environment.Path_LibraryCache, speedup_branch, false, false)
                copy_lang(speedup_branch)
        }

        stage('android'){
                if(platforms.contains("android")){
                        switch_platform("android")
                }
        }

        stage('ios'){                
                if(platforms.contains("ios")){
                        switch_platform("ios")
                }
        }

        stage('win'){
                if(platforms.contains("win")){
                        switch_platform("win64")
                }
        }

        stage('commit'){
                //commit_git(environment.Path_LibraryCache, speedup_branch, "update library: "+branch,"",false)
        }

        stage('end'){
                close_unity()
        }
}