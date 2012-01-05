package beertool

class RecipeGrainsController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [recipeGrainsInstanceList: RecipeGrains.list(params), recipeGrainsInstanceTotal: RecipeGrains.count()]
    }

    def create = {
        def recipeGrainsInstance = new RecipeGrains()
        recipeGrainsInstance.properties = params
        return [recipeGrainsInstance: recipeGrainsInstance]
    }

    def save = {
        def recipeGrainsInstance = new RecipeGrains(params)
        if (recipeGrainsInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'recipeGrains.label', default: 'RecipeGrains'), recipeGrainsInstance.id])}"
            redirect(action: "show", id: recipeGrainsInstance.id)
        }
        else {
            render(view: "create", model: [recipeGrainsInstance: recipeGrainsInstance])
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

    def edit = {
        def recipeGrainsInstance = RecipeGrains.get(params.id)
        if (!recipeGrainsInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'recipeGrains.label', default: 'RecipeGrains'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [recipeGrainsInstance: recipeGrainsInstance]
        }
    }

    def update = {
        def recipeGrainsInstance = RecipeGrains.get(params.id)
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

    def delete = {
        def recipeGrainsInstance = RecipeGrains.get(params.id)
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
