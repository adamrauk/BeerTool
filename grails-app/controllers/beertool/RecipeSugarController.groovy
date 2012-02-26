package beertool
import grails.plugins.springsecurity.Secured;

class RecipeSugarController {
	def springSecurityService
	
	private currentUser() {
		 def auth = springSecurityService.authentication
		 def userInstance
		 if (auth.name != 'anonymousUser') {
			  userInstance= User.get(springSecurityService.principal.id)
		 }
		 else {userInstance=User.findWhere(username: 'anonymous')}
		return userInstance}

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
	static scaffold=true
	
    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [recipeSugarInstanceList: RecipeSugar.list(params), recipeSugarInstanceTotal: RecipeSugar.count()]
    }

	@Secured(['IS_AUTHENTICATED_FULLY'])
    def create = {
        def recipeSugarInstance = new RecipeSugar()
        recipeSugarInstance.properties = params
		def currentUser=currentUser()
		def recipeUser = recipeSugarInstance.recipe.user
		if (currentUser != recipeUser) {
			flash.message = "You are not allowed to modify someone else's recipe."
			redirect(controller: "main", action: "index")
		 }
		else {
			return [recipeSugarInstance: recipeSugarInstance]
		}
    }

    def save = {
        def recipeSugarInstance = new RecipeSugar(params)
		def isNewSugar = recipeSugarInstance ? Sugar.findAllByName(recipeSugarInstance.name) : []
		if (!isNewSugar.size()) {
			def sugarInstance = new Sugar()
			sugarInstance.name=recipeSugarInstance.name
			sugarInstance.save(flush:true)
		}
        if (recipeSugarInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'recipeSugar.label', default: 'RecipeSugar'), recipeSugarInstance.id])}"
			redirect(controller: 'recipe', action: 'edit', params: ['id':recipeSugarInstance.recipe.id])
        }
        else {
            render(view: "create", model: [recipeSugarInstance: recipeSugarInstance])
        }
    }

	def showsugar = {
		def sugarvals=[]
		sugarvals = Sugar.withCriteria {
			order("name", "asc")
		}
		render(contentType: 'text/json') {
			sugarvals.name
			}
	}

    def show = {
        def recipeSugarInstance = RecipeSugar.get(params.id)
        if (!recipeSugarInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'recipeSugar.label', default: 'RecipeSugar'), params.id])}"
            redirect(action: "list")
        }
        else {
            [recipeSugarInstance: recipeSugarInstance]
        }
    }

	@Secured(['IS_AUTHENTICATED_FULLY'])
    def edit = {
        def recipeSugarInstance = RecipeSugar.get(params.id)
		def currentUser=currentUser()
		def recipeUser = recipeSugarInstance.recipe.user
		if (currentUser != recipeUser) {
			flash.message = "You are not allowed to modify someone else's recipe."
			redirect(action: "show", id: params.id)
		 }
		else {
	        if (!recipeSugarInstance) {
	            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'recipeSugar.label', default: 'RecipeSugar'), params.id])}"
	            redirect(action: "list")
	        }
	        else {
	            return [recipeSugarInstance: recipeSugarInstance]
	        }
		}
    }

	@Secured(['IS_AUTHENTICATED_FULLY'])
    def update = {
        def recipeSugarInstance = RecipeSugar.get(params.id)
		def currentUser=currentUser()
		def recipeUser = recipeSugarInstance.recipe.user
		if (currentUser != recipeUser) {
			flash.message = "You are not allowed to modify someone else's recipe."
			redirect(controller: "main", action: "index")
		 }
		else {
	        if (recipeSugarInstance) {
	            if (params.version) {
	                def version = params.version.toLong()
	                if (recipeSugarInstance.version > version) {
	                    
	                    recipeSugarInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'recipeSugar.label', default: 'RecipeSugar')] as Object[], "Another user has updated this RecipeSugar while you were editing")
	                    render(view: "edit", model: [recipeSugarInstance: recipeSugarInstance])
	                    return
	                }
	            }
	            recipeSugarInstance.properties = params
	            if (!recipeSugarInstance.hasErrors() && recipeSugarInstance.save(flush: true)) {
	                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'recipeSugar.label', default: 'RecipeSugar'), recipeSugarInstance.id])}"
	                redirect(action: "show", id: recipeSugarInstance.id)
	            }
	            else {
	                render(view: "edit", model: [recipeSugarInstance: recipeSugarInstance])
	            }
	        }
	        else {
	            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'recipeSugar.label', default: 'RecipeSugar'), params.id])}"
	            redirect(action: "list")
	        }
		}
    }

	@Secured(['IS_AUTHENTICATED_FULLY'])
    def delete = {
        def recipeSugarInstance = RecipeSugar.get(params.id)
		def currentUser=currentUser()
		def recipeUser = recipeSugarInstance.recipe.user
		if (currentUser != recipeUser) {
			flash.message = "You are not allowed to modify someone else's recipe."
			redirect(action: "show", id: params.id)
		 }
		else {
	        if (recipeSugarInstance) {
	            try {
	                recipeSugarInstance.delete(flush: true)
	                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'recipeSugar.label', default: 'RecipeSugar'), params.id])}"
	                redirect(action: "list")
	            }
	            catch (org.springframework.dao.DataIntegrityViolationException e) {
	                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'recipeSugar.label', default: 'RecipeSugar'), params.id])}"
	                redirect(action: "show", id: params.id)
	            }
	        }
	        else {
	            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'recipeSugar.label', default: 'RecipeSugar'), params.id])}"
	            redirect(action: "list")
	        }
		}
    }
}
