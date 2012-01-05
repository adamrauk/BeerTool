package beertool

class HopsController {

	static scaffold = true
    def index = { redirect(action:list, params:params) }
}
