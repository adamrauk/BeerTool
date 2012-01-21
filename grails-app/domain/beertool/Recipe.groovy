package beertool

class Recipe {

    static constraints = {
		name(nullable: true)
		style()
		brewingMethod(inList:["All Grain","Partial Extract","Extract"])
		targetSG(nullable:true)
		targetVolume(nullable:true)
		mashTemperature(nullable:true)
    }

	static belongsTo = [user:User]
	static hasMany = [recipeGrains:RecipeGrains,
		recipeHops:RecipeHops, recipeSugar:RecipeSugar, recipeYeast:RecipeYeast, batch:Batch]
		
	String name
	String style
	BigDecimal targetSG
	BigDecimal targetVolume
	String brewingMethod
	BigDecimal mashTemperature
	
	
	
	String toString() {name + "(" + style + ")"}
}
