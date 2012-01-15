package beertool

import grails.converters.JSON

class BatchController {
    static scaffold = true
	
    def index = { redirect(action:list, params:params) }

	def plotSample = {
		def batch = Batch.get(params.id)
		def datavals = batch ? Measurement.findAllByBatch(batch) : []
		def datavals2 = datavals as JSON
		def batchid = params.id
		render(view:'temperaturePlot', model: [datavals: datavals2, batchid: batchid])
	}
	def plotSample2 = {
		def batch = Batch.get(params.batch.id)
		def datavals = batch ? Measurement.findAllByBatch(batch) : []
		def datavals2 = datavals as JSON
		def batchid = params.batch.id
		render(view:'temperaturePlot', model: [datavals: datavals2, batchid: batchid])
	}

	def getSample = {
		def batch = Batch.get(params.id)
		def datavals = batch ? Measurement.findAllByBatch(batch) : []
		render datavals as JSON
					
		//THIS QUERY CAUSED SCREW	ED UP SORTING	
		/*def batch = Batch.get(params.id)
		def datavals = batch?.measurement
		render datavals as JSON*/
	}

	def getSG = {
		def batch = Batch.get(params.id)
		def datavals = batch ? Measurement.findAllByBatch(batch) : []
		def recipevals = batch ? Recipe.getAll() : []
		def datavals2 = datavals as JSON
		def recipevals2 = recipevals as JSON
		def batchid = params.id
		render(view:'custom', model: [datavals: datavals2, recipevals: recipevals2, 
				batchid: batchid])
	}
		

	def getAll = {
		def recipe = Recipe.getAll() as JSON
		render(recipe)
	}

	def getRecipe = {
		def batch = Batch.get(params.id)
		def datavals = batch ? Recipe.getAll() : []
		def datavals2 = datavals as JSON
		render datavals2
	}


	
		
}
