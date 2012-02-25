package beertool

class RecipeGrainsController {

	static scaffold=true
    def index = { redirect(action:list, params:params) }

	def save = {
		def recipeGrainsInstance = new RecipeGrains(params)
		def isNewGrain = recipeGrainsInstance ? Grains.findAllByName(recipeGrainsInstance.name) : []
		if (!isNewGrain.size()) {
			def grainsInstance = new Grains()
			grainsInstance.name=recipeGrainsInstance.name
			grainsInstance.save(flush:true)
		}

		if (!recipeGrainsInstance.hasErrors()&&recipeGrainsInstance.save(flush:true)) {
			flash.message = "Recipe Grains added"
			redirect(controller: 'recipe', action: 'edit', params: ['id':recipeGrainsInstance.recipe.id])}
	}
	
	def showgrains = {
		def grainvals=[]
		grainvals = Grains.withCriteria {
			order("name", "asc")
		}
		render(contentType: 'text/json') {
			grainvals.name
			}
	}

}
