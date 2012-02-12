import beertool.*

class BootStrap {
	def springSecurityService
	
    def init = { servletContext ->
		def hops1 = new beertool.Hops(name: "Cascade",
				alphaAcid: 8
			)
		hops1.save()
		def grains1 = new beertool.Grains(name: "Pilsener", color: 1.5, sugarPotential: 1.040)
		grains1.save()
		def sugar1 = new beertool.Sugar(name: "DME", sugarPotential: 1.045)
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
