package beertool
import grails.plugins.springsecurity.Secured;

class RecipeYeastController {
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
        [recipeYeastInstanceList: RecipeYeast.list(params), recipeYeastInstanceTotal: RecipeYeast.count()]
    }

	@Secured(['IS_AUTHENTICATED_FULLY'])
    def create = {
        def recipeYeastInstance = new RecipeYeast()
        recipeYeastInstance.properties = params
		def currentUser=currentUser()
		def recipeUser = recipeYeastInstance.recipe.user
		if (currentUser != recipeUser) {
			flash.message = "You are not allowed to modify someone else's recipe."
			redirect(controller: "main", action: "index")
		 }
		else {
			return [recipeYeastInstance: recipeYeastInstance]
		}
    }

    def save = {
        def recipeYeastInstance = new RecipeYeast(params)
		def isNewYeast = recipeYeastInstance ? Yeast.findAllByName(recipeYeastInstance.name) : []
		if (!isNewYeast.size()) {
			def yeastInstance = new Yeast()
			yeastInstance.name=recipeYeastInstance.name
			yeastInstance.save(flush:true)
		}
        if (recipeYeastInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'recipeYeast.label', default: 'RecipeYeast'), recipeYeastInstance.id])}"
			redirect(controller: 'recipe', action: 'edit', params: ['id':recipeYeastInstance.recipe.id])
        }
        else {
            render(view: "create", model: [recipeYeastInstance: recipeYeastInstance])
        }
    }
	def showyeast = {
		def yeastvals=[]
		yeastvals = Yeast.withCriteria {
			order("name", "asc")
		}
		render(contentType: 'text/json') {
			yeastvals.name
			}
	}

    def show = {
        def recipeYeastInstance = RecipeYeast.get(params.id)
        if (!recipeYeastInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'recipeYeast.label', default: 'RecipeYeast'), params.id])}"
            redirect(action: "list")
        }
        else {
            [recipeYeastInstance: recipeYeastInstance]
        }
    }

	@Secured(['IS_AUTHENTICATED_FULLY'])
    def edit = {
        def recipeYeastInstance = RecipeYeast.get(params.id)
		def currentUser=currentUser()
		def recipeUser = recipeYeastInstance.recipe.user
		if (currentUser != recipeUser) {
			flash.message = "You are not allowed to modify someone else's recipe."
			redirect(action: "show", id: params.id)
		 }
		else {
	        if (!recipeYeastInstance) {
	            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'recipeYeast.label', default: 'RecipeYeast'), params.id])}"
	            redirect(action: "list")
	        }
	        else {
	            return [recipeYeastInstance: recipeYeastInstance]
	        }
		}
    }

	@Secured(['IS_AUTHENTICATED_FULLY'])
    def update = {
        def recipeYeastInstance = RecipeYeast.get(params.id)
		def currentUser=currentUser()
		def recipeUser = recipeYeastInstance.recipe.user
		if (currentUser != recipeUser) {
			flash.message = "You are not allowed to modify someone else's recipe."
			redirect(controller: "main", action: "index")
		 }
		else {
	        if (recipeYeastInstance) {
	            if (params.version) {
	                def version = params.version.toLong()
	                if (recipeYeastInstance.version > version) {
	                    
	                    recipeYeastInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'recipeYeast.label', default: 'RecipeYeast')] as Object[], "Another user has updated this RecipeYeast while you were editing")
	                    render(view: "edit", model: [recipeYeastInstance: recipeYeastInstance])
	                    return
	                }
	            }
	            recipeYeastInstance.properties = params
	            if (!recipeYeastInstance.hasErrors() && recipeYeastInstance.save(flush: true)) {
	                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'recipeYeast.label', default: 'RecipeYeast'), recipeYeastInstance.id])}"
	                redirect(action: "show", id: recipeYeastInstance.id)
	            }
	            else {
	                render(view: "edit", model: [recipeYeastInstance: recipeYeastInstance])
	            }
	        }
	        else {
	            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'recipeYeast.label', default: 'RecipeYeast'), params.id])}"
	            redirect(action: "list")
	        }
		}
    }

	@Secured(['IS_AUTHENTICATED_FULLY'])
    def delete = {
        def recipeYeastInstance = RecipeYeast.get(params.id)
		def currentUser=currentUser()
		def recipeUser = recipeYeastInstance.recipe.user
		if (currentUser != recipeUser) {
			flash.message = "You are not allowed to modify someone else's recipe."
			redirect(action: "show", id: params.id)
		 }
		else {
	        if (recipeYeastInstance) {
	            try {
	                recipeYeastInstance.delete(flush: true)
	                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'recipeYeast.label', default: 'RecipeYeast'), params.id])}"
	                redirect(action: "list")
	            }
	            catch (org.springframework.dao.DataIntegrityViolationException e) {
	                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'recipeYeast.label', default: 'RecipeYeast'), params.id])}"
	                redirect(action: "show", id: params.id)
	            }
	        }
	        else {
	            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'recipeYeast.label', default: 'RecipeYeast'), params.id])}"
	            redirect(action: "list")
	        }
		}
    }
}
