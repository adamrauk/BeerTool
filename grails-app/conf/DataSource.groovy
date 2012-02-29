dataSource {
    pooled = true
  /*  driverClassName = "org.hsqldb.jdbcDriver"
    username = "sa"
    password = ""*/
	driverClassName = "com.mysql.jdbc.Driver"
//	username = "adamrauk_grails" //use this one for brewmashter.com
//	username = "grails" //use this one for localhost
	username = "brewmas_grails" //use this one for jvm.net
	password = "s3cur3" //use this one for brewmashter or jvm.net
//	password = "server" //use this one for localhost
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
//			url = "jdbc:mysql://localhost:3306/beertool_dev?autoreconnect=true"
			url = "jdbc:mysql://brewmashter.jvmhost.net:3306/brewmas_dev?autoreconnect=true"
        }
    }
    test {
        dataSource {
            dbCreate = "create-drop"
            //url = "jdbc:hsqldb:mem:testDb"
			//url = "jdbc:mysql://brewmashter.com:3306/adamrauk_brewmashter_test?autoreconnect=true"
 			url = "jdbc:mysql://brewmashter.jvmhost.net:3306/brewmas_test?autoreconnect=true"
        }
    }
    production {
        dataSource {
            dbCreate = "update"
            //url = "jdbc:hsqldb:file:prodDb;shutdown=true"
// 			url = "jdbc:mysql://brewmashter.com:3306/adamrauk_brewmashter_prod?autoreconnect=true"
 			url = "jdbc:mysql://brewmashter.jvmhost.net:3306/brewmas_prod?autoreconnect=true"
       }
    }
}
