package beertool

class RecipeGrainsController {

	static scaffold=true
    def index = { redirect(action:list, params:params) }

}
