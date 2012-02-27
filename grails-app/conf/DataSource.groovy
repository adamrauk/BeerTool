dataSource {
    pooled = true
  /*  driverClassName = "org.hsqldb.jdbcDriver"
    username = "sa"
    password = ""*/
	driverClassName = "com.mysql.jdbc.Driver"
	username = "adamrauk_grails"
	password = "s3cur3"
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
			url = "jdbc:mysql://brewmashter.com:3306/adamrauk_brewmashter_dev?autoreconnect=true"
        }
    }
    test {
        dataSource {
            dbCreate = "create-drop"
            //url = "jdbc:hsqldb:mem:testDb"
			url = "jdbc:mysql://brewmashter.com:3306/adamrauk_brewmashter_test?autoreconnect=true"
        }
    }
    production {
        dataSource {
            dbCreate = "update"
            //url = "jdbc:hsqldb:file:prodDb;shutdown=true"
 			url = "jdbc:mysql://brewmashter.com:3306/adamrauk_brewmashter_prod?autoreconnect=true"
       }
    }
}
