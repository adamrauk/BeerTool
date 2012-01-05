package beertool

import grails.converters.JSON

class RecipeController {
	static scaffold=true
    def index = { redirect(action:list, params:params) }


}
