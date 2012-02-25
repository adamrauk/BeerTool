import beertool.*

class BootStrap {
	def springSecurityService
	
    def init = { servletContext ->
		def hops=[
			'Admiral (RH 40)': [alphaAcid: 13],
			'Ahtanum': [alphaAcid: 6],
			'Amarillo': [alphaAcid: 9.5],
			'Aurora': [alphaAcid: 8],
			'Bramling Cross': [alphaAcid: 6.5],
			'Brewer\'s Gold': [alphaAcid: 7],
			'Cascade': [alphaAcid: 5],
			'Centennial': [alphaAcid: 9],
			'Chinook': [alphaAcid: 12.5],
			'Cluster': [alphaAcid: 7],
			'Columbus': [alphaAcid: 15],
			'Crystal': [alphaAcid: 3.3],
			'First Gold': [alphaAcid: 7.5],
			'Fuggle': [alphaAcid: 4.5],
			'Galena': [alphaAcid: 12],
			'Golding': [alphaAcid: 5],
			'Hallertauer Mittelfruh': [alphaAcid: 4.5],
			'Hallertauer Tradition': [alphaAcid: 5],
			'Hallertauer Magnum': [alphaAcid: 11],
			'Herald': [alphaAcid: 12],
			'Hersbrucker': [alphaAcid: 3],
			'Kent Golding': [alphaAcid: 5.5],
			'Liberty': [alphaAcid: 4.5],
			'Lublin/Lubelski': [alphaAcid: 4],
			'Mount Hood': [alphaAcid: 3.8],
			'Northern Brewer': [alphaAcid: 8],
			'Nugget': [alphaAcid: 9.7],
			'Perle': [alphaAcid: 6],
			'Phoenix': [alphaAcid: 10],
			'Pioneer': [alphaAcid: 9],
			'Pride of Ringwood': [alphaAcid: 8.5],
			'Progress': [alphaAcid: 6.2],
			'Saaz': [alphaAcid: 4.2],
			'Santiam': [alphaAcid: 6],
			'Shinsu Wase': [alphaAcid: 5.3],
			'Sladek': [alphaAcid: 9.2],
			'Spalter': [alphaAcid: 4.5],
			'Spalter Select': [alphaAcid: 4.5],
			'Sterling': [alphaAcid: 7.5],
			'Strisselspalt': [alphaAcid: 3.5],
			'Styrian Golding': [alphaAcid: 5.2],
			'Taurus': [alphaAcid: 13.5],
			'Tettnanger': [alphaAcid: 4.2],
			'Ultra': [alphaAcid: 2.6],
			'Vanguard': [alphaAcid: 5.5],
			'Whitbread Golding Variety (WGV)': [alphaAcid: 6.2],
			'Willamette': [alphaAcid: 5],
			'Wye Challenger': [alphaAcid: 7.5],
			'Wye Northdown': [alphaAcid: 8.5],
			'Wye Target': [alphaAcid: 11]
			]
		hops.each { name, attributes ->
			def hopInstance = new Hops(
				name: name, alphaAcid: attributes.alphaAcid)
			hopInstance.save(flush:true)
		}
		def grains=[
			'Pilsener Malt (Two-row)',
			'Lager Malt (Two-row)',
			'Lager Malt (Six-row)',
			'Pale Ale Malt',
			'Vienna Malt',
			'Mild Ale Malt',
			'Munich Malt',
			'Aromatic Malt',
			'Amber/Biscuit Malt',
			'Brown Malt',
			'Pale Chocolate Malt',
			'Chocolate Malt',
			'Rostmalz',
			'Black (Patent) Malt',
			'Black Roasted Barley',
			'Dextrine Malt (Cara-Pils)'
		]
		grains.each { name ->
			def grainsInstance = new Grains(
				name: name)
			grainsInstance.save(flush:true)
		}

		
		
		
		def sugar1 = new beertool.Sugar(name: "DME", potentialGravity: 1.045)
		sugar1.save()
		
		def samples = [
			'Alex':[fullName: 'Alex Smith'],
			'Eli':[fullName: 'Eli Manning']]

        def userRole = getOrCreateRole("ROLE_USER")
        def adminRole = getOrCreateRole("ROLE_ADMIN")
		
		def users = User.list() ?: []
		
		if (!users) {
			def adminUser = new User(
				username: "admin",
				password: springSecurityService.encodePassword("s3cur3"),
				enabled: true).save()
			SecUserSecRole.create adminUser, adminRole

			def anonymousUser = new User(
				username: "anonymous",
				password: springSecurityService.encodePassword(""),
				enabled: true).save()
			SecUserSecRole.create anonymousUser, userRole

            samples.each { username, profileAttrs ->
				def user = new User(
					username: username,
					password: springSecurityService.encodePassword("s3cur3"),
					enabled: true)
				user.save(flush:true)
				SecUserSecRole.create user, userRole
			}
		}
	
    }

	
    def destroy = {
    }

	private getOrCreateRole(name) {
		def role = SecRole.findByAuthority(name)
		if (!role) role = new SecRole(authority: name).save()
		if (!role)  println "Unable to save role ${name}"
		return role
	}

}
