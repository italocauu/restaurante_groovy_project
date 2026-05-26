package restaurante

import grails.converters.JSON

class PedidoController {

    PedidoService pedidoService

    // Blinda a API: Garante que as rotas só aceitem o verbo HTTP correto
    static allowedMethods = [
        index : 'GET',
        show  : 'GET',
        save  : 'POST',
        update: 'PUT',
        delete: 'DELETE'
    ]

    // GET /api/clientes
    def index() {
        List<Pedido> pedidos = PedidoService.listar()
        render pedidos as JSON
    }

    // GET /api/clientes/{id}
    def show(Long id) {
        Pedido pedido = PedidoService.buscarPorId(id)
        if (!pedido) {
            response.status = 404
            render([erro: "Pedido não encontrado"] as JSON)
            return
        }
        render pedido as JSON
    }

    // POST /api/clientes
    def save() {
        Map dados = request.JSON
        Pedido pedido = Pedidoservice.cadastrar(dados)

        if (pedido.hasErrors()) {
            response.status = 422
            render([erros: pedido.errors.allErrors.collect { it.defaultMessage }] as JSON)
            return
        }

        response.status = 201
        render pedido as JSON
    }

    // PUT /api/clientes/{id}
    def update(Long id) {
        Map dados = request.JSON
        Pedido pedido = pedidoService.atualizar(id, dados)

        if (!pedido) {
            response.status = 404
            render([erro: "Pedido não encontrado"] as JSON)
            return
        }

        if (pedido.hasErrors()) {
            response.status = 422
            render([erros: pedido.errors.allErrors.collect { it.defaultMessage }] as JSON)
            return
        }

        render pedido as JSON
    }

    // DELETE /api/clientes/{id}
    def delete(Long id) {
        boolean sucesso = pedidoService.deletar(id)
        if (!sucesso) {
            response.status = 404
            render([erro: "Pedido não encontrado"] as JSON)
            return
        }
        
        response.status = 204
        render "" // Status 204 (No Content) não precisa de corpo na resposta
    }
}