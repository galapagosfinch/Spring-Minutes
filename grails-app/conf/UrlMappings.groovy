class UrlMappings {

	static mappings = {
        "/rest/$controller/element/$id"(parseRequest:true){
            action = [GET:"show", DELETE: "delete", PUT: "update"]
        }

        "/rest/$controller/list"(parseRequest:true){
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
