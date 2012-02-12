package beertool

class RecipeHops {

    static constraints = {
		hops(nullable:true)
		name()
		hopUse(inList: ["Boil","Dry Hop","Mash","First Wort","Aroma"])
		alphaAcid()
		amount()
		amountUnits(inList:["oz"])
		boilTime()
		price(nullable:true)
		comment(nullable:true)
    }
	
	static hasMany = [hops:Hops]
	
	Recipe recipe
	Hops hops
	String name
	BigDecimal amount
	String amountUnits
	BigDecimal boilTime
	BigDecimal alphaAcid
	BigDecimal price
	String hopUse = "Boil"
	String comment
	
	String toString() {name + "(" + amount + "oz for " + boilTime + " min)"}
}
