package beertool

class RecipeGrains {

    static constraints = {
		amount()
		amountUnits(inList: ["lb"])
		potentialGravity(nullable:true)
		potentialGravityUnits(nullable:true)
		color(nullable:true)
		colorUnits(inList: ["SRM"])
    }
	
	static hasMany = [grains:Grains]
	static belongsTo = [recipe:Recipe]

	Recipe recipe
	Grains grains
	String name
	BigDecimal amount
	BigDecimal amountUnits
	BigDecimal potentialGravity
	BigDecimal potentialGravityUnits
	BigDecimal color
	BigDecimal colorUnits
		
	String toString() {grains.name + "(" + amount+ " " + amountUnits + ")"}
}
