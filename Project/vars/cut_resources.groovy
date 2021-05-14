library 'Common'
def call(){
    echo "******start cut resources******"

    if(isUnix()){

    }
    else{
        close_unity()
        def src=String.format("%sAssets/Resources", environment.Path_Project)
        def target=environment.Path_Root
        def cmd=String.format("move %s %s", src, target)
        bat cmd
    }
    
    echo "******end cut resources******"
}