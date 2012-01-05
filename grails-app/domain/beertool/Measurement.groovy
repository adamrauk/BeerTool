package beertool

class Measurement {

    static constraints = {
		stage(nullable:true, 
			inList:["Heat water", "Mash", "Heat water for sparge","Sparge", "Bring to boil", "Boil","Cool wort","Pitch Yeast"])
		liquorTemperature(nullable:true)
		liquorVolume(nullable:true)
		wortTemperature(nullable:true)
		wortVolume(nullable:true)
		specificGravity(nullable:true)
		specificGravityTemperature(nullable:true)
		dateAdded(nullable:true, attributes: [precision:"minute"])
    }
	
	static belongsTo = [batch:Batch]
	
	BigDecimal liquorTemperature
	BigDecimal liquorVolume
	BigDecimal wortTemperature
	BigDecimal wortVolume
	BigDecimal specificGravity
	BigDecimal specificGravityTemperature
	String stage
	Date lastUpdated
	Date dateCreated 
	Date dateAdded = new Date()
	
}
