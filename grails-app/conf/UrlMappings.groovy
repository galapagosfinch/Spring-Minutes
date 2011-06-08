class UrlMappings {

	static mappings = {
        "/rest/$controller/element/$id"{
            action = [GET:"show", DELETE: "delete", PUT: "update"]
        }

        "/rest/$controller/list"{
            action = [GET:"list", POST: "save"]
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
