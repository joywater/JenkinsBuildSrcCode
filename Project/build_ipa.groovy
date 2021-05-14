library 'Common'

def rootPath = String.format("%s%s/", environment.Path_XCode_Project, node_name)
def projPath= String.format("%sUnity-iPhone.xcodeproj", rootPath)
def archivePath= String.format("%sgyee", rootPath)
def ipaFolderPath=String.format("%sipa", rootPath)
def optionsPlist=""
def ipaName= ""

node("imac"){        
        stage('archive_dev'){
                sh String.format("security unlock-keychain -p %s %s", environment.Pwd_Mac, environment.Path_KeyChain)
                sh String.format("xcodebuild clean archive -project %s -scheme Unity-iPhone -configuration 'ReleaseForRunning' -UseModernBuildSystem=NO -archivePath %s", projPath, archivePath)
        }
        stage('export_dev'){  
                if(language=="cn" || language =="tw"){
                        optionsPlist=environment.Path_OptionsPlist_Dev_ZH
                }
                else if(language=="en"){
                        optionsPlist=environment.Path_OptionsPlist_Dev_EN
                }
                else{
                        optionsPlist=environment.Path_OptionsPlist_Dev_JP
                }

                ipaName= String.format("gyee_%s_%s_%s_%s.ipa", build_mode, branch, version, build_num)
                sh String.format("xcodebuild -exportArchive -archivePath %s.xcarchive -exportOptionsPlist %s -exportPath %s", archivePath, optionsPlist, ipaFolderPath)
                sh String.format("mv %s/GYEE.ipa %s%s", ipaFolderPath, environment.Path_Ipa, ipaName)
        }
        stage('archive_store'){
                if(build_mode=="release"){                
                        archivePath= String.format("%sgyee_appstore", rootPath)        
                        sh String.format("security unlock-keychain -p %s %s", environment.Pwd_Mac, environment.Path_KeyChain)
                        sh String.format("xcodebuild clean archive -project %s -scheme Unity-iPhone -configuration 'Release' -UseModernBuildSystem=NO -archivePath %s", projPath, archivePath)
                }
        }
        stage('export_store'){ 
                if(build_mode=="release"){
                        if(language=="cn" || language =="tw"){
                                optionsPlist=environment.Path_OptionsPlist_AppStore_ZH
                        }
                        else if(language=="en"){
                                optionsPlist=environment.Path_OptionsPlist_AppStore_EN
                        }
                        else{
                                optionsPlist=environment.Path_OptionsPlist_AppStore_JP
                        }

                        ipaName= String.format("gyee_appstore_%s_%s_%s.ipa", branch, version, build_num)
                        sh String.format("xcodebuild -exportArchive -archivePath %s.xcarchive -exportOptionsPlist %s -exportPath %s", archivePath, optionsPlist, ipaFolderPath)
                        sh String.format("mv %s/GYEE.ipa %s%s", ipaFolderPath, environment.Path_Ipa, ipaName)
                }                
        }
}