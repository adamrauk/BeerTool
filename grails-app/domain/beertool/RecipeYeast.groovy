package beertool

class RecipeYeast {

    static constraints = {
		yeast(nullable:true)
		
    }
	
	Recipe recipe
	Yeast yeast
	String name
	
	String toString() {name}
}
