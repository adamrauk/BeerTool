package beertool
import grails.plugins.springsecurity.Secured;

class RecipeGrainsController {
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
        [recipeGrainsInstanceList: RecipeGrains.list(params), recipeGrainsInstanceTotal: RecipeGrains.count()]
    }

	@Secured(['IS_AUTHENTICATED_FULLY'])
    def create = {
        def recipeGrainsInstance = new RecipeGrains()
        recipeGrainsInstance.properties = params
		def currentUser=currentUser()
		def recipeUser = recipeGrainsInstance.recipe.user
		if (currentUser != recipeUser) {
			flash.message = "You are not allowed to modify someone else's recipe."
			redirect(controller: "main", action: "index")
		 }
		else {
			return [recipeGrainsInstance: recipeGrainsInstance]
		}
	 }

	@Secured(['IS_AUTHENTICATED_FULLY'])
	def save = {
		def recipeGrainsInstance = new RecipeGrains(params)
		def isNewGrain = recipeGrainsInstance ? Grains.findAllByName(recipeGrainsInstance.name) : []
		if (!isNewGrain.size()) {
			def grainsInstance = new Grains()
			grainsInstance.name=recipeGrainsInstance.name
			grainsInstance.save(flush:true)
		}

		if (!recipeGrainsInstance.hasErrors()&&recipeGrainsInstance.save(flush:true)) {
			flash.message = "Recipe Grains added"
			redirect(controller: 'recipe', action: 'edit', params: ['id':recipeGrainsInstance.recipe.id])}
	}
	
	def showgrains = {
		def grainvals=[]
		grainvals = Grains.withCriteria {
			order("name", "asc")
		}
		render(contentType: 'text/json') {
			grainvals.name
			}
	}

    def show = {
        def recipeGrainsInstance = RecipeGrains.get(params.id)
        if (!recipeGrainsInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'recipeGrains.label', default: 'RecipeGrains'), params.id])}"
            redirect(action: "list")
        }
        else {
            [recipeGrainsInstance: recipeGrainsInstance]
        }
    }

	@Secured(['IS_AUTHENTICATED_FULLY'])
    def edit = {
        def recipeGrainsInstance = RecipeGrains.get(params.id)
		def currentUser=currentUser()
		def recipeUser = recipeGrainsInstance.recipe.user
		if (currentUser != recipeUser) {
			flash.message = "You are not allowed to modify someone else's recipe."
			redirect(action: "show", id: params.id)
		 }
		else {
	        if (!recipeGrainsInstance) {
	            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'recipeGrains.label', default: 'RecipeGrains'), params.id])}"
	            redirect(action: "list")
	        }
	        else {
	            return [recipeGrainsInstance: recipeGrainsInstance]
	        }
		}
    }

	@Secured(['IS_AUTHENTICATED_FULLY'])
    def update = {
        def recipeGrainsInstance = RecipeGrains.get(params.id)
		def currentUser=currentUser()
		def recipeUser = recipeGrainsInstance.recipe.user
		if (currentUser != recipeUser) {
			flash.message = "You are not allowed to modify someone else's recipe."
			redirect(controller: "main", action: "index")
		 }
		else {
	        if (recipeGrainsInstance) {
	            if (params.version) {
	                def version = params.version.toLong()
	                if (recipeGrainsInstance.version > version) {
	                    
	                    recipeGrainsInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'recipeGrains.label', default: 'RecipeGrains')] as Object[], "Another user has updated this RecipeGrains while you were editing")
	                    render(view: "edit", model: [recipeGrainsInstance: recipeGrainsInstance])
	                    return
	                }
	            }
	            recipeGrainsInstance.properties = params
	            if (!recipeGrainsInstance.hasErrors() && recipeGrainsInstance.save(flush: true)) {
	                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'recipeGrains.label', default: 'RecipeGrains'), recipeGrainsInstance.id])}"
	                redirect(action: "show", id: recipeGrainsInstance.id)
	            }
	            else {
	                render(view: "edit", model: [recipeGrainsInstance: recipeGrainsInstance])
	            }
	        }
	        else {
	            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'recipeGrains.label', default: 'RecipeGrains'), params.id])}"
	            redirect(action: "list")
	        }
		}
    }

	@Secured(['IS_AUTHENTICATED_FULLY'])
    def delete = {
        def recipeGrainsInstance = RecipeGrains.get(params.id)
		def currentUser=currentUser()
		def recipeUser = recipeGrainsInstance.recipe.user
		if (currentUser != recipeUser) {
			flash.message = "You are not allowed to modify someone else's recipe."
			redirect(action: "show", id: params.id)
		 }
		else {
	        if (recipeGrainsInstance) {
	            try {
	                recipeGrainsInstance.delete(flush: true)
	                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'recipeGrains.label', default: 'RecipeGrains'), params.id])}"
	                redirect(action: "list")
	            }
	            catch (org.springframework.dao.DataIntegrityViolationException e) {
	                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'recipeGrains.label', default: 'RecipeGrains'), params.id])}"
	                redirect(action: "show", id: params.id)
	            }
	        }
	        else {
	            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'recipeGrains.label', default: 'RecipeGrains'), params.id])}"
	            redirect(action: "list")
	        }
	    }
    }
}
