package restaurante

class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        "/"(view:"/index")
        "500"(view:'/error')
        "404"(view:'/notFound')
		
        // === SUAS NOVAS ROTAS MANUAIS DA API ===
        "/api/$Controller"(controller: "cliente", action: "index", method: "GET")
        "/api/$Controller"(controller: "cliente", action: "save", method: "POST")
        "/api/$Controller/$id"(controller: "cliente", action: "show", method: "GET")
        "/api/$Controller/$id"(controller: "cliente", action: "update", method: "PUT")
        "/api/$Controller/$id"(controller: "cliente", action: "delete", method: "DELETE")
    }
}