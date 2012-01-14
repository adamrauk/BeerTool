package beertool

class RecipeGrains {

    static constraints = {
		
		targetTime(nullable:true)
    }
	
	static hasMany = [grains:Grains]
	static belongsTo = [recipe:Recipe]

	Recipe recipe
	Grains grains
	BigDecimal amount
	BigDecimal targetTime
	
	String toString() {grains.name + "(" + amount+ " lb, mash time=" + targetTime + " min)"}
}
