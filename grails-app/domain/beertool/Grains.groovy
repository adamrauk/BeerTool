package beertool

class Grains {

    static constraints = {
		color(nullable:true)
		potentialGravity(nullable:true)
    }
	
	String name
	BigDecimal color
	BigDecimal potentialGravity
	
	String toString() {name}
}
