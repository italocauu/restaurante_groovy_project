package restaurante

class Sobremesa extends Produto {

	Integer calorias
	
    static constraints = {
		calorias min: 0
		
    }
}