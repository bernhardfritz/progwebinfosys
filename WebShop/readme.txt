Vorbereitung: 
	Ausführen des SQL-Scripts /sql/init.sql ausführen, um DB aufzusetzen.

Abgrenzung:
	Wir haben alle GET-/POST-/PUT-/DELETE- Methoden unserer REST-API sowie alle Model-Klassen mit Unit Tests abgedeckt.
	Die Test-Abdeckung haben wir mit Cobertura gemacht.
	Ebenso haben wir Integrationstests für verschiedene Funktionen mit Hilfe von Browser-Simulationen umgesetzt.
	
	Getestet werden müsste noch der DBManager gegen eine Test-Datenbank, das haben wir leider nicht mehr geschafft.
	
Reports:
	Unit-Tests:
		Task: test
		Report: /reports/unit-tests/index.html
	Integration-Tests:
		Task: integrationTest (Achtung: Hierfür müssen der Server mit HTTP-Port 8080 sowie die Datenbank laufen!)
		Report: /reports/integration-tests/index.html
	Abdeckung:
		Task: cobertura (Tests ausführen + Report erstellen)
		Report: /reports/cobertura/index.html


Verwendete Technologien:
	Server: Jetty
	Build: Gradle
	REST: Jersey
	DB: MySQL
	DB-Anbindung: JPA
	Testing: JUnit
	Test-Abdeckung: Cobertura
	Browser-Simulation: Selenium
	Mocking: PowerMockito
	Frontend: AngularJS2 (für die Benutzerverwaltung) + JSP (für den Rest)


Default-User:
  Username: superadmin
  Password: supersecret

  Username: admin
  Password: secret

  Username: user
  Password: password

  Username: guest
  Password: password
  
  Username: bernhard
  Password: secret
  
  Username: mario
  Password: secret
  
  Username: john
  Password: password
  
  Username: fridolin
  Password: password


REST-API:
	GET /WebShop/api/item ... list all items
	POST /WebShop/api/item ... create a new item with specified parameters
	GET /WebShop/api/item/(itemId) ... return information about item with specified itemId
	PUT /WebShop/api/item/(itemId) ... edit item with specified itemId
	DELETE /WebShop/api/item/(itemId) ... delete item with specified itemId
	GET /WebShop/api/item/(itemId)/comment ... list all comments of item with specified itemId
	POST /WebShop/api/item/(itemId)/comment ... comment item with specified itemId
	
	GET /WebShop/api/comment/(commentId) ... return comment with specified commentId
	PUT /WebShop/api/comment/(commentId) ... edit comment with specified commentId
	DELETE /WebShop/api/comment/(commentId) ... delete comment with specified commentId
	
	GET /WebShop/api/category ... list all categories
	POST /WebShop/api/category ... create new category
	PUT /WebShop/api/category/(categoryId) ... edit category
	DELETE /WebShop/api/category/(categoryId) ... delete category
	GET /WebShop/api/category(categoryId)/item ... list all items in this category
	
	GET /WebShop/api/user ... list all users
	POST /WebShop/api/user ... create new user
	PUT /WebShop/api/user/{userId} ... edit user
	DELETE /WebShop/api/user/{userId} ... delete user