package beertool

import grails.converters.JSON

class MeasurementController {

	static scaffold=true
    def index = { redirect(action:list, params:params) }

	def save = {
		def measurement = new Measurement(params)
		if (!measurement.hasErrors()&&measurement.save(flush:true)) {
			flash.message = "Measurement added"
			redirect(action:custom3, params: ['batch.id':measurement.batch.id])}
	}
	
	
	def custom2 = {
		def measurementInstance = new Measurement()
		measurementInstance.properties = params
		def batch = Batch.get(params.batch.id)
		def datavals = batch ? Measurement.findAllByBatch(batch) : []
		def datavals2 = datavals as JSON
		def recipevals = batch ? Recipe.getAll() : []
		def recipevals2 = recipevals as JSON
		
		def batchid = params.batch.id
		render(view: 'custom2', model: [measurementInstance: measurementInstance, datavals: datavals2,
			batchid: batchid,  recipevals: recipevals2])
	}

	//custom3 is an attempt to use AJAX
	def custom3 = {
		def measurementInstance = new Measurement()
		measurementInstance.properties = params
		def batch = Batch.get(params.batch.id)
		def datavals = batch ? Measurement.findAllByBatch(batch) : []
		def datavals2 = datavals as JSON
		def recipevals = batch ? Recipe.getAll() : []
		def recipevals2 = recipevals as JSON
		
		def batchid = params.batch.id

		render(view: 'custom3', model: [measurementInstance: measurementInstance, datavals: datavals2,
			batchid: batchid,  recipevals: recipevals2])
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


	def getStuff = {
		def batch = Batch.get(params.batch.id)
		def recipevals = batch ? Recipe.getAll() : []
		def recipevals2 = recipevals as JSON
		def datavals = batch ? Measurement.findAllByBatch(batch) : []
		def datavals2 = datavals as JSON
		render([recipevals2, datavals2])
	}
		

		def showAll = {
		render Measurement.findAll() as JSON
	}

	def test = {
		render(view:'test')
	}
	
/*	def getSample = {
		def batch = Batch.get(params.id)
		render batch?.batch as JSON
	}*/

	def plotSample = {
		def batch = Batch.get(params.batch.id)
		def datavals = batch ? Measurement.findAllByBatch(batch) : []
		def datavals2 = datavals as JSON
		def batchid = params.batch.id
		render(view:'/batch/temperaturePlot', model: [datavals: datavals2, batchid: batchid])
	}
		
	def custom = {
		render(view:'custom')
	}

	def afterInterceptor  = {model, modelAndView ->
		def batch = Batch.get(params.id)
		def datavals = batch ? Measurement.findAllByBatch(batch) : []
		def datavals2 = datavals as JSON
		def batchid = params.id
		render(view:'batch/temperaturePlot', model: [datavals: datavals2, batchid: batchid])
	}
		
}
