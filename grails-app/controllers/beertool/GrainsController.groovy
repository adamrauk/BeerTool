package beertool

class GrainsController {

	static scaffold=true
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [grainsInstanceList: Grains.list(params), grainsInstanceTotal: Grains.count()]
    }

    def create = {
        def grainsInstance = new Grains()
        grainsInstance.properties = params
        return [grainsInstance: grainsInstance]
    }

    def save = {
        def grainsInstance = new Grains(params)
        if (grainsInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'grains.label', default: 'Grains'), grainsInstance.id])}"
            redirect(action: "show", id: grainsInstance.id)
        }
        else {
            render(view: "create", model: [grainsInstance: grainsInstance])
        }
    }

    def show = {
        def grainsInstance = Grains.get(params.id)
        if (!grainsInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'grains.label', default: 'Grains'), params.id])}"
            redirect(action: "list")
        }
        else {
            [grainsInstance: grainsInstance]
        }
    }

    def edit = {
        def grainsInstance = Grains.get(params.id)
        if (!grainsInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'grains.label', default: 'Grains'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [grainsInstance: grainsInstance]
        }
    }

    def update = {
        def grainsInstance = Grains.get(params.id)
        if (grainsInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (grainsInstance.version > version) {
                    
                    grainsInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'grains.label', default: 'Grains')] as Object[], "Another user has updated this Grains while you were editing")
                    render(view: "edit", model: [grainsInstance: grainsInstance])
                    return
                }
            }
            grainsInstance.properties = params
            if (!grainsInstance.hasErrors() && grainsInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'grains.label', default: 'Grains'), grainsInstance.id])}"
                redirect(action: "show", id: grainsInstance.id)
            }
            else {
                render(view: "edit", model: [grainsInstance: grainsInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'grains.label', default: 'Grains'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def grainsInstance = Grains.get(params.id)
        if (grainsInstance) {
            try {
                grainsInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'grains.label', default: 'Grains'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'grains.label', default: 'Grains'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'grains.label', default: 'Grains'), params.id])}"
            redirect(action: "list")
        }
    }
}
