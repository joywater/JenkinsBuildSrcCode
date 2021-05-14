library 'Common'
node(node_name){
	def hotfixPath = ""
	def hotfixBranch = "master"
	stage('prepare') {
		switch (language) {
			case "zh":
				hotfixPath = environment.Path_Hotfix_Zh
				break
			case "en":
				hotfixPath = environment.Path_Hotfix_Sea
				break
			case "jp":
				hotfixPath = environment.Path_Hotfix_Jp
			break
		}

		close_unity()
		init_git(environment.Path_Root, branch)
		init_git(hotfixPath, hotfixBranch, false)
		if(push_voice=="yes"){
			init_git(environment.Path_Hotfix_Common, hotfixBranch, false)
		}
		copy_lang(language)
	}

	def langs=[language]
	if(language=="zh"){
		langs=["cn","tw"]
	}
	stage('android') {
		set_library("android")
		switch_platform("android")
		for(la in langs){
			call_unity("android", environment.Method_Hotfix, String.format("platform=android lang=%s hotfix_output=%s common_output=%s", la, hotfixPath, environment.Path_Hotfix_Common), "hotfix")
			hotfix_size(la, "android", hotfixPath)
			close_unity()
		}
	}

	stage('ios') {		
		set_library("ios")
		switch_platform("ios")
		for(la in langs){
			call_unity("ios", environment.Method_Hotfix, String.format("platform=ios lang=%s hotfix_output=%s common_output=%s", la, hotfixPath, environment.Path_Hotfix_Common), "hotfix")
			hotfix_size(la, "ios", hotfixPath)
		}
	}

	stage('win') {		
		if(win_hotfix=="yes"){
			set_library("win64")
			switch_platform("win64")
			for(la in langs){
				call_unity("win64", environment.Method_Hotfix, String.format("platform=win64 lang=%s hotfix_output=%s common_output=%s", la, hotfixPath, environment.Path_Hotfix_Common), "hotfix")
				hotfix_size(la, "win64", hotfixPath)
			}
		}
	}

	stage('commit') {
		def log = String.format("hotfix_%s_%s", language, get_version_info("ResVersion"))
		commit_git(environment.Path_Root, branch, log, String.format("%s %s", environment.Path_Bundles, environment.Path_VersionData))

		if(push_res=="yes"){
			commit_git(hotfixPath, hotfixBranch, log)
		}

		if(push_voice=="yes"){
			commit_git(environment.Path_Hotfix_Common, hotfixBranch, String.format("voice update:%s", language))
		}
	}

	stage('end') {
		close_unity()
	}
}