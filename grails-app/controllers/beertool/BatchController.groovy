package beertool

class BatchController {
	static scaffold=true
	def index = { redirect(action:list, params:params) }
	
/* 	def save = {
		def batch = new Batch(params)
		if (!batch.hasErrors()&&Batch.save(flush:true)) {
			flash.message = "Batch added"
			redirect(controller: "measurement", action:"brew", params: ['batch.id':batch.id])}
	}*/

	def create = {
		def batchInstance = new Batch()
		batchInstance.properties = params
//		redirect(controller: "measurement", action: "brew", params: ['batch.id': batchInstance.id])
		return [batchInstance: batchInstance]
	}

}
