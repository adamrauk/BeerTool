package beertool

class RecipeHops {

    static constraints = {
		
    }
	
	static hasMany = [hops:Hops]
	
	Recipe recipe
	Hops hops
	BigDecimal amount
	BigDecimal boilTime
	BigDecimal alphaAcid
	
	String toString() {hops.name + "(" + amount + "oz for " + boilTime + " min)"}
}
