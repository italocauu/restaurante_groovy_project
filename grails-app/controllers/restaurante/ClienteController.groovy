package restaurante

import grails.converters.JSON

class ClienteController {

    ClienteService clienteService

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
        List<Cliente> clientes = clienteService.listar()
        render clientes as JSON
    }

    // GET /api/clientes/{id}
    def show(Long id) {
        Cliente cliente = clienteService.buscarPorId(id)
        if (!cliente) {
            response.status = 404
            render([erro: "Cliente não encontrado"] as JSON)
            return
        }
        render cliente as JSON
    }

    // POST /api/clientes
    def save() {
        Map dados = request.JSON
        Cliente cliente = clienteService.cadastrar(dados)

        if (cliente.hasErrors()) {
            response.status = 422
            render([erros: cliente.errors.allErrors.collect { it.defaultMessage }] as JSON)
            return
        }

        response.status = 201
        render cliente as JSON
    }

    // PUT /api/clientes/{id}
    def update(Long id) {
        Map dados = request.JSON
        Cliente cliente = clienteService.atualizar(id, dados)

        if (!cliente) {
            response.status = 404
            render([erro: "Cliente não encontrado"] as JSON)
            return
        }

        if (cliente.hasErrors()) {
            response.status = 422
            render([erros: cliente.errors.allErrors.collect { it.defaultMessage }] as JSON)
            return
        }

        render cliente as JSON
    }

    // DELETE /api/clientes/{id}
    def delete(Long id) {
        boolean sucesso = clienteService.deletar(id)
        if (!sucesso) {
            response.status = 404
            render([erro: "Cliente não encontrado"] as JSON)
            return
        }
        
        response.status = 204
        render "" // Status 204 (No Content) não precisa de corpo na resposta
    }
}