package beertool;
import grails.plugins.springsecurity.Secured;

class RecipeController {
	def springSecurityService
		
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
	static scaffold=true
	
	private currentUser() {
		 def auth = springSecurityService.authentication
		 def userInstance
		 if (auth.name != 'anonymousUser') {
			  userInstance= User.get(springSecurityService.principal.id)
		 }
		 else {userInstance=User.findWhere(username: 'anonymous')}
		return userInstance}

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
/*		def currentUser=currentUser()
        [recipeInstanceList: currentUser?.recipe?.asList(), recipeInstanceTotal: Recipe.count()]*/
		[recipeInstanceList: Recipe.list(params), recipeInstanceTotal: Recipe.count()]
    }


	def test = {
/*		if (currentUser()) {def currentUser=currentUser()}
		else {currentUser=User.get(2)}*/
		def currentUser = currentUser()
		render(currentUser)
		}
	
    def listall = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        render(view: "list", model: [recipeInstanceList: Recipe.list(params), recipeInstanceTotal: Recipe.count()])
    }

	@Secured(['IS_AUTHENTICATED_FULLY'])
	def listmy = {
		def currentUser=currentUser()
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		render(view: "list", model: [recipeInstanceList: currentUser?.recipe?.asList(), recipeInstanceTotal: Recipe.count()])
	}

	@Secured(['IS_AUTHENTICATED_FULLY'])
    def create = {
        def recipeInstance = new Recipe()
        recipeInstance.properties = params
		def currentUser=currentUser()
        return [recipeInstance: recipeInstance, currentUser: currentUser]
    }

    def save = {
        def recipeInstance = new Recipe(params)
		def currentUser=currentUser();
        if (recipeInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'recipe.label', default: 'Recipe'), recipeInstance.id])}"
 			redirect(action: 'edit', params: ['id':recipeInstance.id])
        }
        else {
            render(view: "create", model: [recipeInstance: recipeInstance])
        }
    }

    def show = {
        def recipeInstance = Recipe.get(params.id)
        if (!recipeInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'recipe.label', default: 'Recipe'), params.id])}"
            redirect(action: "list")
        }
        else {
            [recipeInstance: recipeInstance]
        }
    }

	@Secured(['IS_AUTHENTICATED_FULLY'])
    def edit = {
        def recipeInstance = Recipe.get(params.id)
		def currentUser=currentUser()
		def recipeUser=recipeInstance.user
		if (currentUser != recipeUser) {
	       flash.message = "You are not allowed to edit someone else's recipe."
           redirect(action: "list")
		}
		else {
			if (!recipeInstance) {
				flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'recipe.label', default: 'Recipe'), params.id])}"
				redirect(action: "list")
			}
			else {
				return [recipeInstance: recipeInstance, currentUser: currentUser]
			}
		}
    }

	@Secured(['IS_AUTHENTICATED_FULLY'])
    def update = {
        def recipeInstance = Recipe.get(params.id)
		def currentUser=currentUser()
		def recipeUser = recipeInstance.user
		if (currentUser != recipeUser) {
			flash.message = "You are not allowed to delete someone else's recipe."
			redirect(action: "list")
		 }
		else {

	        if (recipeInstance) {
	            if (params.version) {
	                def version = params.version.toLong()
	                if (recipeInstance.version > version) {
	                    
	                    recipeInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'recipe.label', default: 'Recipe')] as Object[], "Another user has updated this Recipe while you were editing")
	                    render(view: "edit", model: [recipeInstance: recipeInstance, currentUser: currentUser])
	                    return
	                }
	            }
	            recipeInstance.properties = params
	            if (!recipeInstance.hasErrors() && recipeInstance.save(flush: true)) {
	                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'recipe.label', default: 'Recipe'), recipeInstance.id])}"
	                redirect(action: "show", id: recipeInstance.id)
	            }
	            else {
	                render(view: "edit", model: [recipeInstance: recipeInstance, currentUser: currentUser])
	            }
	        }
	        else {
	            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'recipe.label', default: 'Recipe'), params.id])}"
	            redirect(action: "list")
	        }
		}
    }

	@Secured(['IS_AUTHENTICATED_FULLY'])
    def delete = {
        def recipeInstance = Recipe.get(params.id)
		def currentUser = currentUser()
		def recipeUser = recipeInstance.user
		if (currentUser != recipeUser) {
			flash.message = "You are not allowed to delete someone else's recipe."
			redirect(action: "list")
		 } 
		else {
	        if (recipeInstance) {
	            try {
	                recipeInstance.delete(flush: true)
	                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'recipe.label', default: 'Recipe'), params.id])}"
	                redirect(action: "list")
	            }
	            catch (org.springframework.dao.DataIntegrityViolationException e) {
	                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'recipe.label', default: 'Recipe'), params.id])}"
	                redirect(action: "show", id: params.id)
	            }
	        }
	        else {
	            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'recipe.label', default: 'Recipe'), params.id])}"
	            redirect(action: "list")
	        }
		}
    }
}
