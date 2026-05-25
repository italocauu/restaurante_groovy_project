package restaurante

class Bebida extends Produto{
	
	Double liquido
	String unidade
	
    static constraints = {
		 
		unidade nullable:false, blank: false, inList:["L","ml"]
    }
}