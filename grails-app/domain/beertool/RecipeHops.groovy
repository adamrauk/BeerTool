package beertool

class RecipeHops {

    static constraints = {
		name()
		hopUse(inList: ["Boil","Dry Hop","Mash","First Wort","Aroma"])
		alphaAcid(nullable:true)
		amount()
		amountUnits(inList:["oz"])
		boilTime(nullable:true)
		price(nullable:true)
		comment(nullable:true)
    }

	static belongsTo = [recipe:Recipe]
		
	Recipe recipe
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
