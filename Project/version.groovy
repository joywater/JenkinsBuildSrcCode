library 'Common'
node(node_name){
        stage('prepare'){
                close_unity()
                init_git(environment.Path_Root, branch)
        }

        stage('end'){
                def filePath= String.format("%sAssets/Resources/Version.json", environment.Path_Project)
                def version = readJSON file: filePath
                if(operation == "view"){                        
                        echo version.toString()
                }
               else{
                       def log = ""
                       if(operation == "edit"){
                               if(app_version!=""){
                                        version["AppVersion"]=app_version
                                        log = String.format("set app_version: %s ", app_version)
                                }
                                if(ios_build!=""){
                                        version["IosBuild"]=ios_build
                                        log += String.format("set ios_build: %s ", ios_build)
                                }
                                if(android_build!=""){
                                        version["AndroidBuild"]=android_build.toInteger()
                                        log += String.format("set android_build: %s ", android_build)
                                }
                                if(res_version!=""){
                                        version["ResVersion"]=res_version
                                        log += String.format("set res_version: %s ", res_version)
                                }
                       }
                       else{
                               def (v1, v2, v3) = version["ResVersion"].tokenize('.')
                               v3 = (v3.toInteger()+1).toString()
                               version["ResVersion"]=[v1, v2, v3].join('.')
                               log += String.format("set res_version: %s ", version["ResVersion"])
                       }

                       writeJSON file: filePath, json: version
                       commit_git(environment.Path_Root, branch, log)
               }
        }
}