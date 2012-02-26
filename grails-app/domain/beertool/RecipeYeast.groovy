package beertool

class RecipeYeast {

    static constraints = {
		name(nullable:true)
		price(nullable:true)
		comment(nullable:true)
    }
	
	static belongsTo = [recipe:Recipe]

	Recipe recipe
	String name
	BigDecimal price
	String comment
	
	String toString() {name}
}
