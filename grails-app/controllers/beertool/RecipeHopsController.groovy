package beertool

class RecipeHopsController {
	static scaffold=true
    def index = { redirect(action:list, params:params) }
	
	def save = {
		def recipeHopsInstance = new RecipeHops(params)
		if (!recipeHopsInstance.hasErrors()&&recipeHopsInstance.save(flush:true)) {
			flash.message = "Recipe Hops added"
			redirect(controller: 'recipe', action: 'edit', params: ['id':recipeHopsInstance.recipe.id])}
	}

}
