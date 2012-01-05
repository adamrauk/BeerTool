package beertool

class RecipeSugar {

    static constraints = {
		boilTime(nullable:true)
    }
	
	static hasMany = [sugar:Sugar]
	
	Recipe recipe
	Sugar sugar
	BigDecimal amount
	BigDecimal boilTime
	
	String toString() {sugar.name + "(" + amount + " lb, boil time=" + boilTime + "min)" }
}
