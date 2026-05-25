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
        "/api/clientes"(controller: "cliente", action: "index", method: "GET")
        "/api/clientes"(controller: "cliente", action: "save", method: "POST")
        "/api/clientes/$id"(controller: "cliente", action: "show", method: "GET")
        "/api/clientes/$id"(controller: "cliente", action: "update", method: "PUT")
        "/api/clientes/$id"(controller: "cliente", action: "delete", method: "DELETE")
    }
}