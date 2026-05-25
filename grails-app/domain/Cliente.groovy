package restaurante

class Cliente {

	String nome
	String email
	String senha
	String cpf
	
	static hasMany = [pedidos:Pedido, produtosPreferidos:Produto] // Lista de pedidos dentro de cliente um cliente com vários pedidos
	
	
	static mapping = {
		produtosPreferidos joinTable:[name:"preferencias_clientes", key: "id_cliente", column: "id_produto"]	// Faça nas duas
	}
	
    static constraints = {
		nome nullable: false, blank: false
		email email: true, unique: true
		senha size: 6..10 // Veja que, aqui não fara sentido, pois precisa-se validar antes de criptografar.
    	cpf validator: {valor, objeto ->
			// Regra do tipo que retorna true ou false.
			(valor.size() == 11)
		}	
	}
}