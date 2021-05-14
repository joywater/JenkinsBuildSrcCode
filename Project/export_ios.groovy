library 'Common'
node(node_name){
        def xcodePath=String.format("%sgyeelegend_ios/", environment.Path_Export_XCode)
        def hotfixBranch = "master"
        def hotfixPath = environment.Path_Hotfix_Zh
        stage('prepare'){
                close_unity()
                init_git(environment.Path_Root, branch)
                set_library("ios")
                if(package_mode.contains("split")){                        
                        switch (language) {
                                case "en":
                                        hotfixPath = environment.Path_Hotfix_Sea
                                        break
                                case "jp":
                                        hotfixPath = environment.Path_Hotfix_Jp
                                break
		        }
                       init_git(hotfixPath, hotfixBranch, false)
                }
                copy_lang(language)
                switch_platform("ios")
        }

        stage('build_bundle'){
                if(rebuild_bundles=="yes"){
                        call_unity("ios", environment.Method_BuildBundle, "platform=ios", "build_bundles")
                        commit_git(environment.Path_Root, branch, "build bundles: " + branch, environment.Path_Bundles)
                }
        }

        stage('export_project'){     
                cut_resources()
                set_library("ios", true)
                del_folder(xcodePath)
                call_unity("ios", environment.Method_Export, String.format("platform=ios output=%s split_res_output=%s", environment.Path_Export_XCode, hotfixPath), "export_project")
                recover_resources()
                close_unity()
        }

        stage('rsync'){
                sync_files(xcodePath, String.format("%s%s", environment.Remote_XCode, node_name), [], true, true)
        }

        stage('build_ipa'){
                def appVersion= get_version_info("AppVersion")
                build job: 'build_ipa', parameters: [string(name: 'branch', value: branch), string(name: 'language', value: language), string(name: 'build_mode', value: build_mode), string(name: 'build_num', value: env.BUILD_NUMBER), string(name: 'node_name', value: node_name), string(name: 'version', value: appVersion)]
        }

        stage('push_split_res'){
                if(package_mode.contains("split") && push_split=="yes"){
                        commit_git(hotfixPath, hotfixBranch, "split ios res update")
                }
        }

        stage('end'){
                
        }
}