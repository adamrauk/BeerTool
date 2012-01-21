package beertool

class Batch {

    static constraints = {
		recipe(nullable:true)
		name(nullable:true)
    }
	static belongsTo = [recipe:Recipe,  user:User]
	static hasMany = [measurement:Measurement]
	
	Recipe recipe
	String name
	Date lastUpdated
	Date dateCreated
	
	String toString() {name}
}
