class UrlMappings {

	static mappings = {
        "/rest/$controller/element/$id"{
            action = [GET:"show"]
        }

        "/rest/$controller/list"{
            action = [GET:"list"]
        }

        "/$controller/$action?/$id?"{
            constraints {
                // apply constraints here
            }
        }

		"/"(view:"/index")
		"500"(view:'/error')
	}
}
