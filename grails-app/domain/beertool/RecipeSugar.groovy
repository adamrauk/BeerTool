package beertool

class RecipeSugar {

    static constraints = {
		sugar(nullable:true)
		name()
		amount()
		amountUnits(inList:["lb","oz"])
		boilTime(nullable:true)
		potentialGravity(nullable:true)
		potentialGravityUnits(inList:["GU"])
		price(nullable:true)
    }
	
	static hasMany = [sugar:Sugar]
	
	Recipe recipe
	Sugar sugar
	String name
	BigDecimal amount
	String amountUnits
	BigDecimal potentialGravity
	String potentialGravityUnits
	BigDecimal boilTime
	BigDecimal price
	
	String toString() {name + "(" + amount + " lb, boil time=" + boilTime + "min)" }
}
