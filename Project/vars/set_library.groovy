library 'Common'
def call(String platform, boolean afterCutResources = false){
    echo "******start set library******"

    def targetLib= afterCutResources? 
                    String.format("%s%s/%s_no_res", environment.Path_LibraryCache,speedup_branch,platform):
                    String.format("%s%s/%s", environment.Path_LibraryCache,speedup_branch,platform)
    close_library()
    link_library(targetLib)

    echo "******end set library******"
}