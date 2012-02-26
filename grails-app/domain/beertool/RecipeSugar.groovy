package beertool

class RecipeSugar {

    static constraints = {
		name()
		amount()
		amountUnits(inList:["lb","oz"])
		boilTime(nullable:true)
		potentialGravity(nullable:true)
		potentialGravityUnits(inList:["GU"])
		price(nullable:true)
		comment(nullable:true)
    }
	
	static belongsTo = [recipe:Recipe]
	
	Recipe recipe
	String name
	BigDecimal amount
	String amountUnits
	BigDecimal potentialGravity
	String potentialGravityUnits
	BigDecimal boilTime
	BigDecimal price
	String comment
	
	String toString() {name + "(" + amount + " lb, boil time=" + boilTime + "min)" }
}
