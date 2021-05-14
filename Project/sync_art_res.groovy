library 'Common'
node(node_name){
        def art_res_path=""
        def art_res_root_path=""
        stage('prepare'){
            close_unity()
            init_svn(environment.Path_Art_Root)
            init_git(environment.Path_Root, branch)            
            //copy_lang(speedup_branch)
        }

        stage('sync_art_res'){
            def dev_res_path=String.format("%sAssets/Resources", environment.Path_Project)
            def art_branch= version=="debug"? "dev":"release_res"
            art_res_path=String.format("%s%s/Assets/Resources/GameAssets", environment.Path_Art_Root, art_branch)
            art_res_root_path=String.format("%s%s", environment.Path_Art_Root, art_branch)
            def lang_res_path=String.format("%s%s/LanguageAssets", environment.Path_Art_Root, art_branch)
            def local_res_path=String.format("%s%s/Assets/Resources/Localization", environment.Path_Art_Root, art_branch)
            sync_files(art_res_path, dev_res_path, [".svn","*.spriteatlas", "UI/Prefabs", "UI/Prefabs.meta", "Logic", "Logic.meta"])
            sync_files(lang_res_path, environment.Path_Project, [".svn","*.spriteatlas", "en/Assets/Resources/GameAssets/UI/Prefabs", "jp/Assets/Resources/GameAssets/UI/Prefabs"])
            //sync_files(local_res_path, dev_res_path, [".svn"])
        }

        stage('optimize_res'){
            if(optimize=="yes"){
                set_library("win64")
                switch_platform("win64")
                call_unity("win64", environment.Method_DoAfterSyncArtRes, "", "sync_res")
            }
        }

        stage('commit'){
            def revision = get_svn_revision(art_res_root_path)
            def log = String.format("sync_art_res_%s: %s", revision, branch)
            echo revision
            echo branch
            commit_git(environment.Path_Root, branch, log, "")
        }

        stage('end'){
                close_unity()
        }
}