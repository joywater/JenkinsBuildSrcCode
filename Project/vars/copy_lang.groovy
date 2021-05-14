library 'Common'
def call(String lang){
    echo "******start copy lang******"
    if(isUnix()){
        
    }
    else{
        if(lang=="en" || lang=="jp"){
            def src=String.format("%sLanguageAssets/%s/", environment.Path_Project, lang).replace("/","\\")
            def target=environment.Path_Project.replace("/","\\")
            bat String.format("xcopy %s %s /s /y", src, target)
        }        
    }

    echo "******end copy lang******"
}