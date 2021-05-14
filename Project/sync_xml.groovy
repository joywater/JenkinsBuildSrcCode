library 'Common'
node(node_name){
        def revision=""
        def configBranch = ""
        stage('prepare'){
                close_unity()
                init_git(environment.Path_Root, branch)
                
                def svnPath= ""                
                if(language=="zh"){
                        if(version == "release"){
                              configBranch = "master"
                              svnPath= String.format("%srelease_zh", environment.Path_Config)
                        }
                        else{
                                configBranch = "dev"
                                svnPath= String.format("%sdebug_zh", environment.Path_Config)
                        }
                }
                else if(language=="en"){
                        if(version == "release"){
                              configBranch = "master_en"
                              svnPath= String.format("%srelease_en", environment.Path_Config)
                        }
                        else{
                                configBranch = "dev_en"
                                svnPath= String.format("%sdebug_en", environment.Path_Config)
                        }
                }
                else if(language == "jp"){
                        if(version == "release"){
                              configBranch = "master_jp"
                              svnPath= String.format("%srelease_jp", environment.Path_Config)
                        }
                        else{
                                configBranch = "dev_jp"
                                svnPath= String.format("%sdebug_jp", environment.Path_Config)
                        }
                }

                init_git(environment.Path_SubData, configBranch)
                init_svn(svnPath)
                revision=get_svn_revision(svnPath)
                
                def excelPath = String.format("%sExcel_Ini", environment.Path_SubData)
                bat String.format("xcopy \"%s\" \"%s\" /y /e", svnPath, excelPath)
                dir(excelPath){
                        bat "for /f %%i in ('dir /b Client\\*.xlsx') do (move /y %%i Client >nul 2>nul)"
                        bat "for /f %%i in ('dir /b Server\\*.xlsx') do (move /y %%i Server >nul 2>nul)"
                }
        }

        stage('generate_xml'){
                dir(environment.Path_SubData){
                        bat "call GenerateConfigXML.bat"
                }
        }
		
		stage('xml_to_db'){
				dir(environment.Path_SubData){
					if(language=="zh"){
						bat "call Xml2Sqlite.bat 1"
						bat "call Xml2Sqlite.bat 2"
					}
					else if(language=="en"){
						bat "call Xml2Sqlite.bat 3"  
					}
					else if(language == "jp"){
						bat "call Xml2Sqlite.bat 4"  
					}
				}
        }

        stage('commit'){
                def log = String.format("sync_xml_%s_%s_%s", language, version, revision)                
                commit_git(environment.Path_Root, branch, log)
                commit_git(environment.Path_SubData, configBranch, log)
        }

        stage('end'){                
        }

}