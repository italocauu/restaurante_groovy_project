package restaurante

class Pedido {
	
	Date dataHora //Date vale tanto para data quanto hora
	Double valorTotal
	
	Cliente cliente
	
	static hasMany = [itens: ItemPedido]
	
    static constraints = {
		valorTotal min:new Double(0)
		cliente nullable: false
		
    }
	
	static mapping = {	
		cliente column: "id_cliente"
		
	}
}