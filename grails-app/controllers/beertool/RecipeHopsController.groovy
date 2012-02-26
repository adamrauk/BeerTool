package beertool
import grails.plugins.springsecurity.Secured;

class RecipeHopsController {
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
	static scaffold = true
	
    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [recipeHopsInstanceList: RecipeHops.list(params), recipeHopsInstanceTotal: RecipeHops.count()]
    }

	@Secured(['IS_AUTHENTICATED_FULLY'])
    def create = {
        def recipeHopsInstance = new RecipeHops()
        recipeHopsInstance.properties = params
		def currentUser=currentUser()
		def recipeUser = recipeHopsInstance.recipe.user
		if (currentUser != recipeUser) {
			flash.message = "You are not allowed to modify someone else's recipe."
			redirect(controller: "main", action: "index")
		 }
		else {
			return [recipeHopsInstance: recipeHopsInstance]
		}
    }

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

    def show = {
        def recipeHopsInstance = RecipeHops.get(params.id)
        if (!recipeHopsInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'recipeHops.label', default: 'RecipeHops'), params.id])}"
            redirect(action: "list")
        }
        else {
            [recipeHopsInstance: recipeHopsInstance]
        }
    }

	@Secured(['IS_AUTHENTICATED_FULLY'])
    def edit = {
        def recipeHopsInstance = RecipeHops.get(params.id)
		def currentUser=currentUser()
		def recipeUser = recipeHopsInstance.recipe.user
		if (currentUser != recipeUser) {
			flash.message = "You are not allowed to modify someone else's recipe."
			redirect(action: "show", id: params.id)
		 }
		else {
	        if (!recipeHopsInstance) {
	            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'recipeHops.label', default: 'RecipeHops'), params.id])}"
	            redirect(action: "list")
	        }
	        else {
	            return [recipeHopsInstance: recipeHopsInstance]
	        }
		}
    }

	@Secured(['IS_AUTHENTICATED_FULLY'])
    def update = {
        def recipeHopsInstance = RecipeHops.get(params.id)
		def currentUser=currentUser()
		def recipeUser = recipeHopsInstance.recipe.user
		if (currentUser != recipeUser) {
			flash.message = "You are not allowed to modify someone else's recipe."
			redirect(controller: "main", action: "index")
		 }
		else {
	        if (recipeHopsInstance) {
	            if (params.version) {
	                def version = params.version.toLong()
	                if (recipeHopsInstance.version > version) {
	                    
	                    recipeHopsInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'recipeHops.label', default: 'RecipeHops')] as Object[], "Another user has updated this RecipeHops while you were editing")
	                    render(view: "edit", model: [recipeHopsInstance: recipeHopsInstance])
	                    return
	                }
	            }
	            recipeHopsInstance.properties = params
	            if (!recipeHopsInstance.hasErrors() && recipeHopsInstance.save(flush: true)) {
	                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'recipeHops.label', default: 'RecipeHops'), recipeHopsInstance.id])}"
	                redirect(action: "show", id: recipeHopsInstance.id)
	            }
	            else {
	                render(view: "edit", model: [recipeHopsInstance: recipeHopsInstance])
	            }
	        }
	        else {
	            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'recipeHops.label', default: 'RecipeHops'), params.id])}"
	            redirect(action: "list")
	        }
		}
    }

	@Secured(['IS_AUTHENTICATED_FULLY'])
    def delete = {
        def recipeHopsInstance = RecipeHops.get(params.id)
		def currentUser=currentUser()
		def recipeUser = recipeHopsInstance.recipe.user
		if (currentUser != recipeUser) {
			flash.message = "You are not allowed to modify someone else's recipe."
			redirect(action: "show", id: params.id)
		 }
		else {
	        if (recipeHopsInstance) {
	            try {
	                recipeHopsInstance.delete(flush: true)
	                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'recipeHops.label', default: 'RecipeHops'), params.id])}"
	                redirect(action: "list")
	            }
	            catch (org.springframework.dao.DataIntegrityViolationException e) {
	                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'recipeHops.label', default: 'RecipeHops'), params.id])}"
	                redirect(action: "show", id: params.id)
	            }
	        }
	        else {
	            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'recipeHops.label', default: 'RecipeHops'), params.id])}"
	            redirect(action: "list")
	        }
	    }
    }
}
