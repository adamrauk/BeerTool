package beertool

class RecipeGrains {

    static constraints = {
    }
	
	static hasMany = [grains:Grains]
	static belongsTo = [recipe:Recipe]

	Recipe recipe
	Grains grains
	BigDecimal amount
	BigDecimal targetTemperature
	BigDecimal targetTime
	
	String toString() {grains.name + "(" + amount+ " lb, temperature=" + targetTemperature + ", mash time=" + targetTime + " min)"}
}
