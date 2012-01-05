package beertool

class SugarController {
	static scaffold=true
    def index = { redirect(action:list, params:params) }
}
