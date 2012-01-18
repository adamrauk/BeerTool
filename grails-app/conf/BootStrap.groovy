class BootStrap {

    def init = { servletContext ->
		def hops1 = new beertool.Hops(name: "Cascade",
				alphaAcid: 8
			)
		hops1.save()
		def grains1 = new beertool.Grains(name: "Pilsener", color: 1.5, sugarPotential: 1.040)
		grains1.save()
		def sugar1 = new beertool.Sugar(name: "DME", sugarPotential: 1.045)
		sugar1.save()
		
	
    }

	
    def destroy = {
    }
}
