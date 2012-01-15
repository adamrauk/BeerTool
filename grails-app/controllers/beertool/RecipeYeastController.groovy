package beertool

class RecipeYeastController {
	static scaffold=true
    def index = { redirect(action:list, params:params) }
	def save = {
		def recipeYeastInstance = new RecipeYeast(params)
		if (!recipeYeastInstance.hasErrors()&&recipeYeastInstance.save(flush:true)) {
			flash.message = "Recipe Sugar added"
			redirect(controller: 'recipe', action: 'edit', params: ['id':recipeYeastInstance.recipe.id])}
	}
}
