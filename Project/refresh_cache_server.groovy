library 'Common'
node(node_name){
        stage('dev_win'){
                close_unity()
                init_git(environment.Path_Root, "dev")
                refresh_cache("win", "dev")
        }
        stage('dev_android'){
                close_unity()
                refresh_cache("android", "dev")
        }
        stage('dev_ios'){
                close_unity()
                refresh_cache("ios", "dev")
        }

        stage('zh_win'){
                close_unity()
                init_git(environment.Path_Root, "hotfix")
                refresh_cache("win", "zh")
        }
        stage('zh_android'){
                close_unity()
                refresh_cache("android", "zh")
        }
        stage('zh_ios'){
                close_unity()
                refresh_cache("ios", "zh")
        }

        stage('en_win'){
                close_unity()
                init_git(environment.Path_Root, "hotfix_en")
                copy_lang("en")
                refresh_cache("win", "en")
        }
        stage('en_android'){
                close_unity()
                refresh_cache("android", "en")
        }
        stage('en_ios'){
                close_unity()
                refresh_cache("ios", "en")
        }

        stage('jp_win'){
                close_unity()
                init_git(environment.Path_Root, "hotfix_jp")
                copy_lang("jp")
                refresh_cache("win", "jp")
        }
        stage('jp_android'){
                close_unity()
                refresh_cache("android", "jp")
        }
        stage('jp_ios'){
                close_unity()
                refresh_cache("ios", "jp")
        }


        stage("end"){
                close_unity()
        }
}