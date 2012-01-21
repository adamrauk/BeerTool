package beertool

import grails.converters.JSON

class MeasurementController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [measurementInstanceList: Measurement.list(params), measurementInstanceTotal: Measurement.count()]
    }

    def create = {
        def measurementInstance = new Measurement()
        measurementInstance.properties = params
        return [measurementInstance: measurementInstance]
    }

 	def customsave = {
		def measurement = new Measurement(params)
		if (!measurement.hasErrors()&&measurement.save(flush:true)) {
			flash.message = "Measurement added"
			redirect(action:brew, params: ['batch.id':measurement.batch.id])}
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

	def editValue = {
        def measurementInstance = Measurement.get(params.id)
		render(view: 'editValue', model: [measurementInstance: measurementInstance])
	}
    def edit = {
        def measurementInstance = Measurement.get(params.id)
        if (!measurementInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'measurement.label', default: 'Measurement'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [measurementInstance: measurementInstance]
        }
    }

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


	def customdelete = {
		def measurementInstance = Measurement.get(params.id)
		if (measurementInstance) {
			try {
				measurementInstance.delete(flush: true)
				flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'measurement.label', default: 'Measurement'), params.id])}"
				redirect(action:custom3, params: ['batch.id':measurementInstance.batch.id])
			}
			catch (org.springframework.dao.DataIntegrityViolationException e) {
				flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'measurement.label', default: 'Measurement'), params.id])}"
				redirect(action:custom3, params: ['batch.id':measurementInstance.batch.id])
			}
		}
		else {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'measurement.label', default: 'Measurement'), params.id])}"
				redirect(action:brew, params: ['batch.id':measurementInstance.batch.id])
		}
	}
	

    def delete = {
        def measurementInstance = Measurement.get(params.id)
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

		render(view: 'custom3', model: [measurementInstance: measurementInstance, batchInstance: batchInstance,
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
		def hopvals = batch ? RecipeHops.getAll() : []
		def hopvals2 = hopvals as JSON
		render(hopvals2)
	}

	
	def getBatch = {
		def batch = Batch.get(params.batch.id)
		def recipevals = batch ? Recipe.getAll() : []
		def recipevals2 = recipevals as JSON
		def datavals = batch ? Measurement.findAllByBatch(batch) : []
		def datavals2 = datavals as JSON
		def hopvals = batch ? RecipeHops.getAll() : []
		def hopvals2 = hopvals as JSON
		render([recipevals2, datavals2, hopvals2])
	}
		

}
