package beertool

class RecipeHopsController {
	static scaffold=true
    def index = { redirect(action:list, params:params) }
}
