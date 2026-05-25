package restaurante

import grails.gorm.transactions.Transactional

@Transactional
class ClienteService {

    // LER TODOS
    List<Cliente> listar() {
        Cliente.list()
    }

    // LER UM ESPECÍFICO
    Cliente buscarPorId(Long id) {
        Cliente.get(id)
    }

    // CRIAR
    Cliente cadastrar(Map dados) {
        if (dados.senha) {
            dados.senha = dados.senha.reverse()
        }
        
        Cliente cliente = new Cliente(dados)
        // O flush garante a ida ao banco, mas não estoura erro fatal se a validação (ex: CPF) falhar
        cliente.save(flush: true) 
        return cliente
    }

    // ATUALIZAR
    Cliente atualizar(Long id, Map dados) {
        Cliente cliente = Cliente.get(id)
        if (!cliente) return null // Retorna nulo se não achar

        // Se mandou uma senha nova na atualização, inverte também
        if (dados.senha) {
            dados.senha = dados.senha.reverse()
        }

        cliente.properties = dados
        cliente.save(flush: true)
        return cliente
    }

    // DELETAR
    boolean deletar(Long id) {
        Cliente cliente = Cliente.get(id)
        if (!cliente) return false
        
        cliente.delete(flush: true)
        return true
    }
}