package restaurante

class Produto {
	
	String nome
	Double preco
	Estoque estoque
	
	/*
	// static hasOne = [estoque:Estoque] Apenas a tabela Estoque vai receber
									     a chave estrangeira da classe mãe, note que a
								         tabela mãe que usa o hasOne 
	*/
	static hasMany = [clientes: Cliente, itens: ItemPedido]
	
	static belongsTo = [Cliente]
	
    static constraints = {
		nome nullable:false, blank:false
		preco min: new Double(0)
    }
	
	static mapping = {
		table name: "produto"	
		estoque column: "id_estoque"
		id column: "id_produto"
		
		produtosPreferidos joinTable:[name:"preferencias_clientes", key: "id_produto", column: "id_cliente"]
	}
}

	// O mapping também é usado pra fazer mapeamento das classes de domínio de um projeto já existente em um banco de dados