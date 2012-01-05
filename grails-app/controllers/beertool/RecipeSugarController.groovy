package beertool

class RecipeSugarController {

	static scaffold = true
	def index = { redirect(action:list, params:params) }
}
