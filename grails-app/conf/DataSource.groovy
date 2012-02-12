dataSource {
    pooled = true
  /*  driverClassName = "org.hsqldb.jdbcDriver"
    username = "sa"
    password = ""*/
	driverClassName = "com.mysql.jdbc.Driver"
	username = "grails"
	password = "server"
}
hibernate {
    cache.use_second_level_cache = true
    cache.use_query_cache = true
    cache.provider_class = 'com.opensymphony.oscache.hibernate.OSCacheProvider' /*'net.sf.ehcache.hibernate.EhCacheProvider'*/
}
// environment specific settings
environments {
    development {
        dataSource {
            dbCreate = "create-drop" // one of 'create', 'create-drop','update'
            //url = "jdbc:hsqldb:file:devDB"
			url = "jdbc:mysql://localhost:3306/beertool_dev?autoreconnect=true"
        }
    }
    test {
        dataSource {
            dbCreate = "create-drop"
            //url = "jdbc:hsqldb:mem:testDb"
			url = "jdbc:mysql://localhost:3306/beertool_dev?autoreconnect=true"
        }
    }
    production {
        dataSource {
            dbCreate = "update"
            //url = "jdbc:hsqldb:file:prodDb;shutdown=true"
 			url = "jdbc:mysql://localhost:3306/beertool_dev?autoreconnect=true"
       }
    }
}
