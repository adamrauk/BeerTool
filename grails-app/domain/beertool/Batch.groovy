package beertool

class Batch {

    static constraints = {
		recipe(nullable:true)
		name(nullable:true)
		volume(nullable:false)
		mashEfficiency(nullable:true)
    }
	static belongsTo = [recipe:Recipe,  user:User]
	static hasMany = [measurement:Measurement]
	
	Recipe recipe
	User user
	String name
	BigDecimal volume
	Date lastUpdated
	Date dateCreated
	String mashEfficiency
	
	String toString() {id + " - " + name + "(" + user + ")"}
}
