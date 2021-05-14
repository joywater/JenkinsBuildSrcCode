library 'Common'
node(node_name){
        stage('prepare'){
            close_unity()
            init_svn(environment.Path_Localization_Root)
            init_git(environment.Path_Root, branch)
        }

        stage('sync'){
            def languageRes=""
            def languageDev=""
            switch(language) {
                case "cn":
                    languageRes="Chinese"
                    languageDev="Chinese"
                    break
                case "tw":
                    languageRes="ChineseTw"
                    languageDev="ChineseTw"
                    break
                case "en":
                    languageRes="English"
                    languageDev="English"
                    break
                case "jp":
                    languageRes="Japanese"
                    languageDev="Japan"
                    break
            }
            def languageResPath=String.format("%s%s/", environment.Path_Localization_Root, languageRes)
            def languageDevPath=String.format("%sAssets/Resources/Localization/%s/", environment.Path_Project, languageDev)
            //if(language=="cn"){
               //sync_files(languageDevPath, languageResPath, ["*.meta"], false)
            //}
            //else{
                sync_files(languageResPath, languageDevPath, ["*.meta"], false)
            //}
        }

        stage('commit'){
            //if(language=="cn"){
                //commit_svn(environment.Path_Localization_Root, "sync localization")
            //}
            //else{
                commit_git(environment.Path_Root, branch, "sync localization")
            //}
        }

        stage('end'){
        }
}