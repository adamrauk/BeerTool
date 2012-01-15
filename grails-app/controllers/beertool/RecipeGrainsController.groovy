package beertool

class RecipeGrainsController {

	static scaffold=true
    def index = { redirect(action:list, params:params) }

	def save = {
		def recipeGrainsInstance = new RecipeGrains(params)
		if (!recipeGrainsInstance.hasErrors()&&recipeGrainsInstance.save(flush:true)) {
			flash.message = "Recipe Grains added"
			redirect(controller: 'recipe', action: 'edit', params: ['id':recipeGrainsInstance.recipe.id])}
	}
	
}
