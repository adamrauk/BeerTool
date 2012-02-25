package beertool
import grails.converters.JSON

class RecipeHopsController {
	static scaffold=true
    def index = { redirect(action:list, params:params) }
	
	def save = {
		def recipeHopsInstance = new RecipeHops(params)
		def isNewHop = recipeHopsInstance ? Hops.findAllByName(recipeHopsInstance.name) : []
		if (!isNewHop.size()) {
			def hopsInstance = new Hops()
			hopsInstance.name=recipeHopsInstance.name
			hopsInstance.save(flush:true)
		}
		if (!recipeHopsInstance.hasErrors()&&recipeHopsInstance.save(flush:true)) {
			flash.message = "Recipe Hops added"
			redirect(controller: 'recipe', action: 'edit', params: ['id':recipeHopsInstance.recipe.id])}
	}
	def showhops = {
		def hopvals=[]
		hopvals = Hops.withCriteria {
			order("name", "asc")
		}
		render(contentType: 'text/json') {
			hopvals.name
			}
	}
}
