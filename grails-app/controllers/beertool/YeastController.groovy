package beertool

class YeastController {
	static scaffold=true
    def index = { redirect(action:list, params:params) }
}
