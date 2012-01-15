package beertool

class RecipeController {

    static scaffold=true

    def index = {
        redirect(action: "list", params: params)
    }

	def save = {
		def recipeInstance = new Recipe(params)
		if (!recipeInstance.hasErrors()&&recipeInstance.save(flush:true)) {
			flash.message = "Recipe Grains added"
			redirect(action: 'edit', params: ['id':recipeInstance.id])}
	}

	
}
