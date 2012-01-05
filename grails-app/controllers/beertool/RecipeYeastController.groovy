package beertool

class RecipeYeastController {
	static scaffold=true
    def index = { redirect(action:list, params:params) }
}
