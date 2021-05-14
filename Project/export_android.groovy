library 'Common'
node(node_name){
        def projPath=String.format("%sgyeelegend_android/gyeelegend", environment.Path_Export_Android)
        def hotfixBranch = "master"
        def hotfixPath = environment.Path_Hotfix_Zh
        stage('prepare'){
                close_unity()
                init_git(environment.Path_Root, branch)
                set_library("android")
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
                switch_platform("android")
        }

        stage('build_bundle'){
                if(rebuild_bundles=="yes"){
                        call_unity("android", environment.Method_BuildBundle, "platform=android", "build_bundles")
                        commit_git(environment.Path_Root, branch, "build bundles: " + branch, environment.Path_Bundles)
                }                
        }

        stage('export_project'){     
                cut_resources()
                set_library("android", true)
                //使用对应语言的manifest文件（保证OBB的unity.build-id的准确性）
                def manifestSrcPath = String.format("%sData/Android_Language/%s/src/main/AndroidManifest.xml", environment.Path_Project, language)
                def manifestTargetPath = String.format("%sAssets/Plugins/Android/AndroidManifest.xml", environment.Path_Project)
                move(manifestSrcPath, manifestTargetPath)
                del_folder(String.format("%sgyeelegend_android", environment.Path_Export_Android))
                call_unity("android", environment.Method_Export, String.format("platform=android output=%s split_res_output=%s", environment.Path_Export_Android, hotfixPath), "export_project")
                recover_resources()
        }

        stage('build_apk'){                
                sync_files(String.format("%sData/Android_Language/%s/", environment.Path_Project, language), projPath, [], false)
                sync_files(String.format("%sData/Android_Language/code/", environment.Path_Project), projPath, [], false)
                dir(projPath){
                        bat String.format("%s clean", environment.Path_Gradle_Bin)
                        bat String.format("%s assembleRelease", environment.Path_Gradle_Bin)
                        bat String.format("%s --stop", environment.Path_Gradle_Bin)                       
                }
        }

        stage('rsync'){
                //同步apk
                def appVersion= get_version_info("AppVersion")
                //def build= get_version_info("AndroidBuild")
                def srcApk=String.format("%s/build/outputs/apk/release/gyeelegend-release.apk", projPath)
                //def targetApk=String.format("%sGYEE_%s_%s_%s(%s)_%s.apk", environment.Path_Export_Apk, build_mode, branch, appVersion,build, env.BUILD_NUMBER).toLowerCase()
                //sync_files(srcApk, targetApk, [])
                def targetApk=String.format("%sGYEE_%s_%s_%s_%s.apk", environment.Remote_Apk, build_mode, branch, appVersion, env.BUILD_NUMBER).toLowerCase()
                if(pay_mode=="web"){
                        targetApk=String.format("%sGYEE_%s_%s_web_%s_%s.apk", environment.Remote_Apk, build_mode, branch, appVersion, env.BUILD_NUMBER).toLowerCase()
                }
                sync_files(srcApk, targetApk, [], false, true)

                //同步obb
                if(package_mode.contains("obb")){
                        def obbPathFormat= "%smain.%s.com.thegyee.www.obb"
                        if(language=="en"){
                                obbPathFormat= "%smain.%s.com.thegyee.sea1.obb"
                        }
                        else if(language=="jp"){
                                obbPathFormat= "%smain.%s.com.thegyee.jp.obb"
                        }
                        def srcObb = String.format("%s/gyeelegend.main.obb", projPath)
                        def targetObb=String.format(obbPathFormat, environment.Remote_Apk, get_version_info("AndroidBuild"))                        
                        sync_files(srcObb, targetObb, [], false, true)
                }
        }

        stage('push_split_res'){
                if(package_mode.contains("split") && push_split=="yes"){
                        commit_git(hotfixPath, hotfixBranch, "split android res update")
                }
        }

        stage('end'){
                close_unity()
        }
}