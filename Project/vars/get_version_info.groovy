def call(String key){
    def props = readJSON file: String.format("%sAssets/Resources/Version.json", environment.Path_Project)
    return props[key]
}