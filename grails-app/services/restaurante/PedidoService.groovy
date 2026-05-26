package restaurante

import grails.gorm.transactions.Transactional

@Transactional // Garante que ou salva tudo (Pedido + Itens) com sucesso, ou não salva nada.
class PedidoService {

    // Lógica 1: BUSCAR TODOS
    List<Pedido> listar() {
        // Traz todos os pedidos. O Grails já traz os clientes e itens atrelados automaticamente (Lazy Loading).
        return Pedido.list() 
    }

    // Lógica 2: BUSCAR UM SÓ
    Pedido buscarPorId(Long id) {
        return Pedido.get(id)
    }

    // Lógica 3: CRIAR UM PEDIDO COMPLETO (O Coração da Regra de Negócio)
    Pedido criar(Map dados) {
        
        // 1. Verificação de Segurança
        // Se o Bruno não mandar a lista de itens, não podemos abrir um pedido vazio.
        if (!dados.itens || dados.itens.isEmpty()) {
            throw new RuntimeException("Um pedido precisa ter pelo menos um item.")
        }

        // 2. Instancia o Pedido e amarra o Cliente
        // O Grails é inteligente: se em 'dados' vier [cliente: [id: 1]], ele entende o relacionamento.
        Pedido novoPedido = new Pedido()
        novoPedido.cliente = Cliente.get(dados.cliente.id)
        
        // 3. Define a Data e Hora exata de agora
        novoPedido.dataHora = new Date()
        
        // 4. Inicia o contador da conta do cliente
        Double somaTotal = 0.0

        // 5. Orquestração dos Itens
        // Vamos percorrer a lista de itens que veio do Bruno (JSON)
        dados.itens.each { Map dadosItem ->
            
            // Vai no banco e busca o Produto real que o cliente está pedindo
            Produto produtoDb = Produto.get(dadosItem.produto.id)
            
            if (!produtoDb) {
                throw new RuntimeException("Produto com ID ${dadosItem.produto.id} não existe no cardápio.")
            }

            // Cria o item daquele pedido
            ItemPedido novoItem = new ItemPedido()
            novoItem.produto = produtoDb
            novoItem.quantidade = dadosItem.quantidade
            novoItem.observacao = dadosItem.observacao ?: "" // Se não tiver observação, fica vazio
            
            // Regra de Negócio Crítica: Preço Congelado
            // Você NÃO deve confiar no 'valorVenda' que vem do Bruno (um hacker pode mandar R$ 0.01).
            // Você deve puxar o preço oficial do banco de dados na hora da compra!
            novoItem.valorVenda = produtoDb.preco
            
            // Adiciona esse item na lista de itens do Pedido (cascade)
            novoPedido.addToItens(novoItem)
            
            // Matemática da Conta: Soma (quantidade * preço) no total do pedido
            somaTotal += (novoItem.quantidade * novoItem.valorVenda)
            
            /* * Regra de Negócio Futura (Estoque):
             * É AQUI que você faria a baixa do estoque no futuro:
             * produtoDb.estoque.quantidade -= novoItem.quantidade
             * produtoDb.estoque.save()
             */
        }

        // 6. Fecha a conta
        // Guarda a soma total calculada no atributo do Pedido
        novoPedido.valorTotal = somaTotal

        // 7. Salva no Banco de Dados
        // Como o ItemPedido tem o 'belongsTo = [Pedido]', ao salvar o Pedido, 
        // o Grails automaticamente vai dar os INSERTS em todos os Itens também!
        novoPedido.save(flush: true, failOnError: true)
        
        return novoPedido
    }

    // Lógica 4: DELETAR
    boolean deletar(Long id) {
        Pedido pedido = Pedido.get(id)
        if (!pedido) return false
        
        // Como você usou hasMany no Pedido para Itens, ao deletar o Pedido,
        // o PostgreSQL vai apagar todos os Itens associados a ele automaticamente (Cascade Delete)
        pedido.delete(flush: true)
        return true
    }
}