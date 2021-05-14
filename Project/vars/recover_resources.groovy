def call(){
    echo "******start recover resources******"

    if(isUnix()){

    }
    else{
        def src= String.format("%sResources", environment.Path_Root)
        def target= String.format("%sAssets/", environment.Path_Project)
        def cmd=String.format("move %s %s", src, target)
        bat cmd
    }
    
    echo "******end recover resources******"
}