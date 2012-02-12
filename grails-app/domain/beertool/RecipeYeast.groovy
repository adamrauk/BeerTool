package beertool

class RecipeYeast {

    static constraints = {
		yeast(nullable:true)
		name(nullable:true)
		price(nullable:true)
		comment(nullable:true)
    }
	
	Recipe recipe
	Yeast yeast
	String name
	BigDecimal price
	String comment
	
	String toString() {name}
}
