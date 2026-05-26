package restaurante

class ProdutoController {

    static responseFormats = ['json']

	def lista = Produto.list()
}

	def adicionar() {
		
		/*	Criando um novo produto
		 *  e quando isso acontece, há
		 *  a criação de um estoque para ele
		 * 	depois coloco a quantidade atual
		 * 	e depois a quantidade mínima.
		 */
		 
		Produto novoProduto = new Produto()
		novoProduto.preco = 0
		novoProduto.estoque = new Estoque()
		novoProduto.estoque.quantidade = 0
		novoProduto.estoque.quantidadeMinima = 0
		
		render(template:"/produto/form", model: [produto: produto])
	}
