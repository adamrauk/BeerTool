class UrlMappings {

	static mappings = {
		"/$controller/$action?/$id?"{
			constraints {
				// apply constraints here
			}
		}
		"/index2"(view:"/index")
		"/"(controller: "main")
		"500"(view:'/error')
		"/login/$action?"(controller: "login")
		"/logout/$action?"(controller: "logout")
		
	}
}
