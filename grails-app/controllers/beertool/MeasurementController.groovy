package beertool
import grails.plugins.springsecurity.Secured;

import grails.converters.JSON

class MeasurementController {
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

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [measurementInstanceList: Measurement.list(params), measurementInstanceTotal: Measurement.count()]
    }

	def listbatch = {
		def batchInstance = Batch.get(params.batch.id)
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
		render(view:"list", model:[measurementInstanceList: batchInstance?.measurement?.asList(), measurementInstanceTotal: batchInstance?.measurement?.count()])
	}
	
    def create = {
        def measurementInstance = new Measurement()
		def batchInstance = measurementInstance.batch
		def currentUser=currentUser()
		def batchUser=batchInstance.user
		if (currentUser != batchUser) {
	       flash.message = "You are not allowed to edit someone else's batch."
           redirect(action: "list")
		}
		else {
	        measurementInstance.properties = params
	        return [measurementInstance: measurementInstance]
		}
    }

 	def customsave = {
		def measurement = new Measurement(params)
		def batchInstance = measurement.batch
		def currentUser=currentUser()
		def batchUser=batchInstance.user
		if (currentUser != batchUser) {
	       flash.message = "You are not allowed to edit someone else's batch."
           redirect(action: "list")
		}
		else {
			if (!measurement.hasErrors()&&measurement.save(flush:true)) {
				flash.message = "Measurement added"
				redirect(action:brew, params: ['batch.id':measurement.batch.id])
				}
		}
	}
   def save = {
        def measurementInstance = new Measurement(params)
        if (measurementInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'measurement.label', default: 'Measurement'), measurementInstance.id])}"
            redirect(action: "show", id: measurementInstance.id)
        }
        else {
            render(view: "create", model: [measurementInstance: measurementInstance])
        }
    }

   
    def show = {
        def measurementInstance = Measurement.get(params.id)
        if (!measurementInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'measurement.label', default: 'Measurement'), params.id])}"
            redirect(action: "list")
        }
        else {
            [measurementInstance: measurementInstance]
        }
    }

	@Secured(['IS_AUTHENTICATED_FULLY'])
	def editValue = {
        def measurementInstance = Measurement.get(params.id)
		render(view: 'editValue', model: [measurementInstance: measurementInstance])
	}
	@Secured(['IS_AUTHENTICATED_FULLY'])
    def edit = {
        def measurementInstance = Measurement.get(params.id)
		def currentUser = currentUser()
		def measurementUser=measurementInstance.batch.user
		if (currentUser != measurementUser) {
			flash.message = "You are not allowed to edit someone else's batch."
			redirect(action: "list")
		 }
		 else {
	        if (!measurementInstance) {
	            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'measurement.label', default: 'Measurement'), params.id])}"
	            redirect(action: "list")
	        }
	        else {
	            return [measurementInstance: measurementInstance]
	        }
		 }
    }

	@Secured(['IS_AUTHENTICATED_FULLY'])
    def update = {
        def measurementInstance = Measurement.get(params.id)
        if (measurementInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (measurementInstance.version > version) {
                    
                    measurementInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'measurement.label', default: 'Measurement')] as Object[], "Another user has updated this Measurement while you were editing")
                    render(view: "edit", model: [measurementInstance: measurementInstance])
                    return
                }
            }
            measurementInstance.properties = params
            if (!measurementInstance.hasErrors() && measurementInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'measurement.label', default: 'Measurement'), measurementInstance.id])}"
                redirect(action: "show", id: measurementInstance.id)
            }
            else {
                render(view: "edit", model: [measurementInstance: measurementInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'measurement.label', default: 'Measurement'), params.id])}"
            redirect(action: "list")
        }
    }
	
	@Secured(['IS_AUTHENTICATED_FULLY'])
	def customupdate = {
		def measurementInstance = Measurement.get(params.id)
		if (measurementInstance) {
			if (params.version) {
				def version = params.version.toLong()
				if (measurementInstance.version > version) {
					
					measurementInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'measurement.label', default: 'Measurement')] as Object[], "Another user has updated this Measurement while you were editing")
					render(view: "edit", model: [measurementInstance: measurementInstance])
					return
				}
			}
			measurementInstance.properties = params
			if (!measurementInstance.hasErrors() && measurementInstance.save(flush: true)) {
				flash.message = "${message(code: 'default.updated.message', args: [message(code: 'measurement.label', default: 'Measurement'), measurementInstance.id])}"
				redirect(action:brew, params: ['batch.id':measurementInstance.batch.id])
			
			}
			else {
				render(view: "edit", model: [measurementInstance: measurementInstance])
			}
		}
		else {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'measurement.label', default: 'Measurement'), params.id])}"
			redirect(action: "list")
		}
	}


	@Secured(['IS_AUTHENTICATED_FULLY'])
	def customdelete = {
		def measurementInstance = Measurement.get(params.id)
		def currentUser = currentUser()
		def measurementUser=measurementInstance.batch.user
		if (currentUser != measurementUser) {
			flash.message = "You are not allowed to edit someone else's batch."
			redirect(action: "list")
		 }
		 else {
			if (measurementInstance) {
				try {
					measurementInstance.delete(flush: true)
					flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'measurement.label', default: 'Measurement'), params.id])}"
					redirect(action:brew, params: ['batch.id':measurementInstance.batch.id])
				}
				catch (org.springframework.dao.DataIntegrityViolationException e) {
					flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'measurement.label', default: 'Measurement'), params.id])}"
					redirect(action:brew, params: ['batch.id':measurementInstance.batch.id])
				}
			}
			else {
				flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'measurement.label', default: 'Measurement'), params.id])}"
					redirect(action:brew, params: ['batch.id':measurementInstance.batch.id])
			}
		 }
	}
	

	@Secured(['IS_AUTHENTICATED_FULLY'])
    def delete = {
        def measurementInstance = Measurement.get(params.id)
		def currentUser = currentUser()
		def measurementUser=measurementInstance.batch.user
		if (currentUser != measurementUser) {
			flash.message = "You are not allowed to edit someone else's batch."
			redirect(action: "list")
		 }
		 else {
	        if (measurementInstance) {
	            try {
	                measurementInstance.delete(flush: true)
	                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'measurement.label', default: 'Measurement'), params.id])}"
	                redirect(action: "list")
	            }
	            catch (org.springframework.dao.DataIntegrityViolationException e) {
	                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'measurement.label', default: 'Measurement'), params.id])}"
	                redirect(action: "show", id: params.id)
	            }
	        }
	        else {
	            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'measurement.label', default: 'Measurement'), params.id])}"
	            redirect(action: "list")
	        }
		 }
    }
	

	//custom3 is an attempt to use AJAX
	def brew = {
		def measurementInstance = new Measurement()
		measurementInstance.properties = params
		def batchInstance = Batch.get(params.batch.id)
		def datavals = batchInstance ? Measurement.findAllByBatch(batchInstance) : []
		def datavals2 = datavals as JSON
		def recipeInstance = batchInstance.recipe
		def recipevals = batchInstance ? Recipe.getAll() : []
		def recipevals2 = recipevals as JSON
		def batchid = params.batch.id
		render(view: 'custom3', model: [measurementInstance: measurementInstance, 
			batchInstance: batchInstance,
			datavals: datavals2,
			batchid: batchid,  recipevals: recipevals2, recipeInstance: recipeInstance])
	}

	def getMeasurements = {
		def batch = Batch.get(params.batch.id)
		def datavals = batch ? Measurement.findAllByBatch(batch) : []
		def datavals2 = datavals as JSON
		render(datavals2)
	}

	def getRecipe = {
		def batch = Batch.get(params.batch.id)
		def recipevals = batch ? Recipe.getAll() : []
		def recipevals2 = recipevals as JSON
		render(recipevals2)
	}

	def getHops = {
		def batch = Batch.get(params.batch.id)
		def recipeInstance = batch.recipe
		def hopvals = recipeInstance ? RecipeHops.getAll() : []
		def hopvals2 = hopvals as JSON
		def recipe = recipeInstance.recipeHops as JSON
		render(recipe)
	}

	
	def getBatch = {
		def batch = Batch.get(params.batch.id)
		def recipeInstance = batch.recipe
		def recipevals = batch ? Recipe.getAll() : []
		def recipevals2 = recipevals as JSON
		def datavals = batch ? Measurement.findAllByBatch(batch) : []
		def datavals2 = datavals as JSON
		def hopvals = recipeInstance.recipeHops
		def hopvals2 = hopvals as JSON
		def grainvals = recipeInstance.recipeGrains
		def grainvals2 = grainvals as JSON
		render([recipevals2, datavals2, hopvals2, grainvals2])
	}
		

}
