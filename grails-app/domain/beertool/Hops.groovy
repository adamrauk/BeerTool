package beertool

class Hops {

    static constraints = {
		alphaAcid(nullable:true)
    }
	
	
	String name
	BigDecimal alphaAcid

	String toString() {name + " (" + alphaAcid + "%)" }
}
