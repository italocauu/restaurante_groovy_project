package restaurante

class ItemPedido {
	/*
	 * Veja que essa classe especifica, ela existe porque é uma relação 1 para n, quer dizer: um produto pode ser pedido várias vezes
	 * e um produto pode ser pedido várias vezes.
	 */
	
	Integer quantidade
	Double valorVenda
	String observacao
	
	Produto produto // Só um produto em itemPedido
	Pedido pedido	// Só um pedido em itemPedido
	
	static beLongsTo = [Pedido] // Relação de muito pra muitos,não precisa, mas facilita na	 hora de salvar;
	
    static constraints = {
		quantidade min: 0
		valorVenda min: new Double(0)
		observacao nullable:true, blank: true
    }


	static mapping = {
		
		produto (column: "id_produto")
		pedido (column: "id_pedido")
	}
}