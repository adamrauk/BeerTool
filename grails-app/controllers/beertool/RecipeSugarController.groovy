package beertool

class RecipeSugarController {

	static scaffold = true
	def index = { redirect(action:list, params:params) }

	def save = {
		def recipeSugarInstance = new RecipeSugar(params)
		if (!recipeSugarInstance.hasErrors()&&recipeSugarInstance.save(flush:true)) {
			flash.message = "Recipe Sugar added"
			redirect(controller: 'recipe', action: 'edit', params: ['id':recipeSugarInstance.recipe.id])}
	}

}
