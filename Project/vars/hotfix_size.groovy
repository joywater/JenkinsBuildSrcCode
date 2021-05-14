library 'Common'
def call(String lang, String platform, String output){
    echo "******start print hotfix size******"
    
    if(isUnix()){
        
    }
    else{
        lang = lang.replace("cn","zh")
        def target_file = String.format("%sv2/%s/%s/hotfix_list_%s.txt", output, platform, lang, lang)
        def exists = fileExists target_file
        if(exists.toString() == "true")
        {
            def txt = readYaml file: target_file
            def lines = txt.split(';')
            def latest = lines[lines.size()-1].split(',')
            def name = latest[0]
            def size = latest[1] as Long
            size = size/(1024*1024)
            def log = String.format("hotfix size(%s %s): %s %sMB", platform, lang, name, size.toString())    
            echo log    
        }
    }

    echo "******end print hotfix size******"
}