library 'Common'
node(node_name){
        def svnPath=String.format("%s%s", environment.Path_Svn_Windows, dev_debug=="no"?language:"debug")

        stage('prepare'){
                close_unity()
                init_git(environment.Path_Root, branch)
                set_library("win64")
                copy_lang(language)
                switch_platform("win64")
        }

        stage('build_bundle'){
                if(rebuild_bundles=="yes"){
                        call_unity("win64", environment.Method_BuildBundle, "platform=win64", "build_bundles")
                        commit_git(environment.Path_Root, branch, "build bundles: " + branch, environment.Path_Bundles)
                }                
        }

        stage('export'){     
                cut_resources()
                set_library("win64", true)
                del_folder(environment.Path_Export_Windows_64)
                call_unity("win64", environment.Method_Export, String.format("platform=win64 output=%s", environment.Path_Export_Windows_64), "export_project")
                recover_resources()
        }

        stage('signature'){
                if(signature_dll_exe=="yes"){
                        call_signatur(environment.Path_Signature_exe)
                        call_signatur(environment.Path_Signature_dll)
                        call_signatur(environment.Path_Signature_GameAssembly_dll)
                        call_signatur(environment.Path_Signature_WinPixEventRuntime_dll)
                }
        }

        stage('clear_path'){
                if(package_mode=="pc"){
                        del_folder(environment.Path_DesignerXmlConfig)
                }
        }

        stage('export_exe'){
                if(setup_exe=="yes"){
                        call_nsis()
                }
        }

        stage('signature'){
                if(signature_dll_exe=="yes"){
                        if(setup_exe=="yes"){
                                call_signatur(environment.Path_Signature_Setup_exe)
                        }
                }
        }

        stage('rsyncPcExe'){
                if(setup_exe=="yes"){
                        def appVersion=get_version_info("AppVersion")
                        def targetApk=String.format("%sGYEE_%s_%s_%s_%s_%s.exe", environment.Remote_Pc, build_mode, branch, appVersion, language, env.BUILD_NUMBER).toLowerCase()
                        sync_files(environment.Path_Signature_Setup_exe, targetApk, [], false, true)
                }
        }

        stage('rsync'){
                init_svn(svnPath)                
                sync_files(environment.Path_Export_Windows_64, svnPath, [".svn"])
        }

        stage('commit'){
                commit_svn(svnPath, String.format("designer_%s_%s", branch, language))
        }

        stage('end'){
                close_unity()
        }
}