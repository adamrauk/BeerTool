package beertool

class RecipeGrains {

    static constraints = {
		name(nullable:true)
		amount()
		amountUnits(inList: ["lb"])
		potentialGravity(nullable:true)
		potentialGravityUnits(inList: ["GU"])
		color(nullable:true)
		colorUnits(inList: ["SRM"])
		price(nullable:true)
		recipe(nullable:true)
		comment(nullable:true)
		
    }
	
	static belongsTo = [recipe:Recipe]

	Recipe recipe
	String name
	BigDecimal amount
	String amountUnits
	BigDecimal potentialGravity
	String potentialGravityUnits
	BigDecimal color
	String colorUnits
	BigDecimal price
	String comment
			
	String toString() {name + "(" + amount+ " " + amountUnits + ")"}
}
