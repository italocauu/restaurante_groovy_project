package restaurante

class Estoque {
		
	Integer quantidade //Integer para valores nulos
	Integer quantidadeMinima //Segunda nomeclatura maiúscula
	Produto produto
	
    static constraints = {
		quantidade min: 0
		quantidadeMinima: 0
		produto nullable: false
    }
	
	static mapping = {
		table name: "estoque" // altera o nome na tabela
		produto column: "produto"
		id column: "id_estoque" // altera a nomeação do id a qual vai ter icremento
	}
}