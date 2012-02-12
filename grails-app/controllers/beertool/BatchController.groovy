package beertool
import grails.plugins.springsecurity.Secured;

class BatchController {
	def springSecurityService
	
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

	private currentUser() {
		def auth = springSecurityService.authentication
		def userInstance
		if (auth.name != 'anonymousUser') {
			 userInstance= User.get(springSecurityService.principal.id)
		}
		else {userInstance=User.findWhere(username: 'anonymous')}
	   return userInstance
	}

	def saveEfficiency = {
		def batchInstance=Batch.get(params.id)
		def currentUser=currentUser()
		def batchUser = batchInstance.user
		if (currentUser != batchUser) {
			flash.message = "You are not allowed to modify someone else's batch."
			redirect(action: "list")
		 }
		else {
			batchInstance.mashEfficiency=params.efficiency
			if (batchInstance.save(flush: true)) {
				flash.message = "${message(code: 'default.created.message', args: [message(code: 'batch.label', default: 'Batch'), batchInstance.id])}"
//				redirect(action: "show", id: batchInstance.id)
				redirect(controller: "measurement", action:"brew", params: ['batch.id':batchInstance.id])
			}
			else {
				render(view: "create", model: [batchInstance: batchInstance])
			}
		}
	}
	
    def index = {
        redirect(action: "list", params: params)
    }

	@Secured(['IS_AUTHENTICATED_FULLY'])
    def list = {
		def currentUser=currentUser()
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        render(view: "list", model: [batchInstanceList: currentUser?.batch?.asList(), batchInstanceTotal: Batch.count()])
		
    }
	def listall = {
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		[batchInstanceList: Batch.list(params), batchInstanceTotal: Batch.count()]
	}

	@Secured(['IS_AUTHENTICATED_FULLY'])
	def listmy = {
		def currentUser=currentUser()
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		render(view: "list", model: [batchInstanceList: currentUser?.batch?.asList(), batchInstanceTotal: Batch.count()])
	}

	def create = {
		def batchInstance = new Batch()
		def currentUser = currentUser()
		batchInstance.properties = params
//		redirect(controller: "measurement", action: "brew", params: ['batch.id': batchInstance.id])
		return [batchInstance: batchInstance, currentUser: currentUser]
	}

    def save = {
        def batchInstance = new Batch(params)
        if (batchInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'batch.label', default: 'Batch'), batchInstance.id])}"
//            redirect(action: "show", id: batchInstance.id)
			redirect(controller: "measurement", action:"brew", params: ['batch.id':batchInstance.id])
        }
        else {
            render(view: "create", model: [batchInstance: batchInstance])
        }
    }
	

    def show = {
        def batchInstance = Batch.get(params.id)
        if (!batchInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'batch.label', default: 'Batch'), params.id])}"
            redirect(action: "list")
        }
        else {
            [batchInstance: batchInstance]
        }
    }

    def edit = {
        def batchInstance = Batch.get(params.id)
		def currentUser = currentUser()
		def batchUser=batchInstance.user
		if (currentUser != batchUser) {
	       flash.message = "You are not allowed to edit someone else's batch."
           redirect(action: "list")
		}
		else {
	        if (!batchInstance) {
	            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'batch.label', default: 'Batch'), params.id])}"
	            redirect(action: "list")
	        }
	        else {
	            return [batchInstance: batchInstance, currentUser: currentUser]
	        }
		}
    }

    def update = {
        def batchInstance = Batch.get(params.id)
		def currentUser = currentUser()
		def batchUser = batchInstance.user
		if (currentUser != batchUser) {
			flash.message = "You are not allowed to delete someone else's batch."
			redirect(action: "list")
		 }
		else {
		
	        if (batchInstance) {
	            if (params.version) {
	                def version = params.version.toLong()
	                if (batchInstance.version > version) {
	                    
	                    batchInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'batch.label', default: 'Batch')] as Object[], "Another user has updated this Batch while you were editing")
	                    render(view: "edit", model: [batchInstance: batchInstance, currentUser: currentUser])
	                    return
	                }
	            }
	            batchInstance.properties = params
	            if (!batchInstance.hasErrors() && batchInstance.save(flush: true)) {
	                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'batch.label', default: 'Batch'), batchInstance.id])}"
	                redirect(action: "show", id: batchInstance.id)
	            }
	            else {
	                render(view: "edit", model: [batchInstance: batchInstance, currentUser: currentUser])
	            }
	        }
	        else {
	            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'batch.label', default: 'Batch'), params.id])}"
	            redirect(action: "list")
	        }
		}
    }

	@Secured(['IS_AUTHENTICATED_FULLY'])
    def delete = {
        def batchInstance = Batch.get(params.id)
		def currentUser=currentUser()
		def batchUser = batchInstance.user
		if (currentUser != batchUser) {
			flash.message = "You are not allowed to delete someone else's batch."
			redirect(action: "list")
		 } 
		else {
	        if (batchInstance) {
	            try {
	                batchInstance.delete(flush: true)
	                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'batch.label', default: 'Batch'), params.id])}"
	                redirect(action: "list")
	            }
	            catch (org.springframework.dao.DataIntegrityViolationException e) {
	                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'batch.label', default: 'Batch'), params.id])}"
	                redirect(action: "show", id: params.id)
	            }
	        }
	        else {
	            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'batch.label', default: 'Batch'), params.id])}"
	            redirect(action: "list")
	        }
	    }
    }
}
