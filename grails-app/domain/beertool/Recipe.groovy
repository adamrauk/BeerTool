package beertool


class Recipe {

    static constraints = {
		name()
		style()
		brewingMethod(inList:["All Grain","Partial Extract","Extract"])
		targetSG(nullable:true)
		targetVolume(nullable:true)
		mashTemperature(nullable:true)
    }

	static hasMany = [batch:Batch, recipeGrains:RecipeGrains,
		recipeHops:RecipeHops, recipeSugar:RecipeSugar, recipeYeast:RecipeYeast]
	
	String name
	String style
	BigDecimal targetSG
	BigDecimal targetVolume
	String brewingMethod
	BigDecimal mashTemperature
	
	
	
	String toString() {name + "(" + style + ")"}
}
