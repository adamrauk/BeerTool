package beertool

class RecipeController {

	def springSecurityService
	private currentUser() {
		return User.get(springSecurityService.principal.id)}
		
    static scaffold=true

    def index = {
        redirect(action: "list", params: params)
    }

	def save = {
		def recipeInstance = new Recipe(params)
//		def currentUser=currentUser();
		if (!recipeInstance.hasErrors()&&recipeInstance.save(flush:true)) {
			flash.message = "Recipe Grains added"
			redirect(action: 'edit', params: ['id':recipeInstance.id])}
	}

	
}
