package beertool

class RecipeHops {

    static constraints = {
		hops(nullable:true)
		name()
		alphaAcid()
		amount()
		amountUnits(inList:["oz"])
		boilTime()
    }
	
	static hasMany = [hops:Hops]
	
	Recipe recipe
	Hops hops
	String name
	BigDecimal amount
	String amountUnits
	BigDecimal boilTime
	BigDecimal alphaAcid
	
	String toString() {name + "(" + amount + "oz for " + boilTime + " min)"}
}
