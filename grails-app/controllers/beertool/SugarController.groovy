package beertool
import grails.plugins.springsecurity.Secured;

class SugarController {
	def springSecurityService
	
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [sugarInstanceList: Sugar.list(params), sugarInstanceTotal: Sugar.count()]
    }

	@Secured(['ROLE_ADMIN'])
    def create = {
        def sugarInstance = new Sugar()
        sugarInstance.properties = params
        return [sugarInstance: sugarInstance]
    }

    def save = {
        def sugarInstance = new Sugar(params)
        if (sugarInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'sugar.label', default: 'Sugar'), sugarInstance.id])}"
            redirect(action: "show", id: sugarInstance.id)
        }
        else {
            render(view: "create", model: [sugarInstance: sugarInstance])
        }
    }

    def show = {
        def sugarInstance = Sugar.get(params.id)
        if (!sugarInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'sugar.label', default: 'Sugar'), params.id])}"
            redirect(action: "list")
        }
        else {
            [sugarInstance: sugarInstance]
        }
    }

	@Secured(['ROLE_ADMIN'])
    def edit = {
        def sugarInstance = Sugar.get(params.id)
        if (!sugarInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'sugar.label', default: 'Sugar'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [sugarInstance: sugarInstance]
        }
    }

	@Secured(['ROLE_ADMIN'])
    def update = {
        def sugarInstance = Sugar.get(params.id)
        if (sugarInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (sugarInstance.version > version) {
                    
                    sugarInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'sugar.label', default: 'Sugar')] as Object[], "Another user has updated this Sugar while you were editing")
                    render(view: "edit", model: [sugarInstance: sugarInstance])
                    return
                }
            }
            sugarInstance.properties = params
            if (!sugarInstance.hasErrors() && sugarInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'sugar.label', default: 'Sugar'), sugarInstance.id])}"
                redirect(action: "show", id: sugarInstance.id)
            }
            else {
                render(view: "edit", model: [sugarInstance: sugarInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'sugar.label', default: 'Sugar'), params.id])}"
            redirect(action: "list")
        }
    }

	@Secured(['ROLE_ADMIN'])
    def delete = {
        def sugarInstance = Sugar.get(params.id)
        if (sugarInstance) {
            try {
                sugarInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'sugar.label', default: 'Sugar'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'sugar.label', default: 'Sugar'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'sugar.label', default: 'Sugar'), params.id])}"
            redirect(action: "list")
        }
    }
}
