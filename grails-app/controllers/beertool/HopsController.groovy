package beertool
import grails.plugins.springsecurity.Secured;

class HopsController {
	def springSecurityService
	
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [hopsInstanceList: Hops.list(params), hopsInstanceTotal: Hops.count()]
    }

	@Secured(['ROLE_ADMIN'])
    def create = {
        def hopsInstance = new Hops()
        hopsInstance.properties = params
        return [hopsInstance: hopsInstance]
    }

    def save = {
        def hopsInstance = new Hops(params)
        if (hopsInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'hops.label', default: 'Hops'), hopsInstance.id])}"
            redirect(action: "show", id: hopsInstance.id)
        }
        else {
            render(view: "create", model: [hopsInstance: hopsInstance])
        }
    }

    def show = {
        def hopsInstance = Hops.get(params.id)
        if (!hopsInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'hops.label', default: 'Hops'), params.id])}"
            redirect(action: "list")
        }
        else {
            [hopsInstance: hopsInstance]
        }
    }

	@Secured(['ROLE_ADMIN'])
    def edit = {
        def hopsInstance = Hops.get(params.id)
        if (!hopsInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'hops.label', default: 'Hops'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [hopsInstance: hopsInstance]
        }
    }

	@Secured(['ROLE_ADMIN'])
    def update = {
        def hopsInstance = Hops.get(params.id)
        if (hopsInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (hopsInstance.version > version) {
                    
                    hopsInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'hops.label', default: 'Hops')] as Object[], "Another user has updated this Hops while you were editing")
                    render(view: "edit", model: [hopsInstance: hopsInstance])
                    return
                }
            }
            hopsInstance.properties = params
            if (!hopsInstance.hasErrors() && hopsInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'hops.label', default: 'Hops'), hopsInstance.id])}"
                redirect(action: "show", id: hopsInstance.id)
            }
            else {
                render(view: "edit", model: [hopsInstance: hopsInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'hops.label', default: 'Hops'), params.id])}"
            redirect(action: "list")
        }
    }

	@Secured(['ROLE_ADMIN'])
    def delete = {
        def hopsInstance = Hops.get(params.id)
        if (hopsInstance) {
            try {
                hopsInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'hops.label', default: 'Hops'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'hops.label', default: 'Hops'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'hops.label', default: 'Hops'), params.id])}"
            redirect(action: "list")
        }
    }
}
