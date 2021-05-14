library 'Common'
node(node_name){
        def svnPath=String.format("%s%s", environment.Path_Svn_Windows, dev_debug=="no"?language:"debug")

        stage('prepare'){
                close_unity()
                init_git(environment.Path_Root, branch)
                set_library("win32")
                copy_lang(language)
                switch_platform("win32")
        }

        stage('build_bundle'){
                if(rebuild_bundles=="yes"){
                        call_unity("win32", environment.Method_BuildBundle, "platform=win32", "build_bundles")
                        commit_git(environment.Path_Root, branch, "build bundles: " + branch, environment.Path_Bundles)
                }                
        }

        stage('export'){     
                cut_resources()
                set_library("win32", true)
                del_folder(environment.Path_Export_Windows)
                call_unity("win32", environment.Method_Export, String.format("platform=win32 output=%s", environment.Path_Export_Windows), "export_project")
                recover_resources()
        }

        stage('rsync'){
                init_svn(svnPath)                
                sync_files(environment.Path_Export_Windows, svnPath, [".svn"])
        }

        stage('commit'){
                commit_svn(svnPath, String.format("designer_%s_%s", branch, language))
        }

        stage('end'){
                close_unity()
        }
}