package beertool

class Grains {

    static constraints = {
		color(nullable:true)
		sugarPotential(nullable:true)
    }
	
	String name
	BigDecimal color
	BigDecimal sugarPotential
	
	String toString() {name}
}
