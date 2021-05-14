library 'Common'
def call(String branch_name){
    def port=1001
    switch(branch_name) {
        case "dev":
            port=1001
            break
        case "zh":
            port=1002
            break
        case "en":
            port=1003
            break
        case "jp":
            port=1004
            break
    }

    return String.format("-CacheServerIPAddress %s:%s", environment.Unity_Cache_Server_Ip, port)
}