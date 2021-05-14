library 'Common'
node(node_name){
        stage('prepare'){
                close_unity()
                init_git(environment.Path_Root, branch)
                copy_lang(language)
        }

        stage('android'){
                if(platforms.contains("android")){
                        set_library("android")
                        switch_platform("android")
                        call_unity("android", environment.Method_BuildBundle, "platform=android", "build_bundles")
                        close_unity()
                }
        }

        stage('ios'){
                if(platforms.contains("ios")){
                        set_library("ios")
                        switch_platform("ios")
                        call_unity("ios", environment.Method_BuildBundle, "platform=ios", "build_bundles")
                        close_unity()
                }
        }

        stage('win'){
                if(platforms.contains("win64")){
                        set_library("win64")
                        switch_platform("win64")
                        call_unity("win64", environment.Method_BuildBundle, "platform=win64", "build_bundles")
                        close_unity()
                }
        }

        stage('commit'){
                commit_git(environment.Path_Root, branch, "build bundles: " + branch, environment.Path_Bundles)
        }

        stage('end'){
                close_unity()
        }
}