Abgrenzung:
	Wir haben alle GET-Methoden unserer REST-API mit Unit Tests abgedeckt.
	Bei den POST-/PUT-/DELETE-Methoden haben wir leider nicht gewusst, wie wir diese testen hätten können.
	Die Test-Abdeckung haben wir mit Cobertura gemacht.
	Ebenso haben wir einen Integrationstest mit Hilfe einer Browser-Simulation umgesetzt.
	
	Getestet werden müsste noch der DBManager sowie die oben genannten Methoden.
	Wir haben leider nicht gewusst, wie wir diese Tests umsetzen, deswegen fehlen sie.
	
Reports:
	Unit-Tests:
		Task: test
		Report: /build/reports/tests
	Integration-Tests:
		Task: integrationTest
		Report: /build/reports/tests
	Abdeckung:
		Task: cobertura (Tests ausführen + Report erstellen)
		Report: /build/reports/cobertura


Technologien:
	Server: Jetty
	Build: Gradle
	REST: Jersey
	DB: MySQL
	DB-Anbindung: JPA
	Testing: JUnit
	Test-Abdeckung: Cobertura
	Browser-Simulation: Selenium


Default-User:
  Username: admin
  Password: secret

  Username: user
  Password: password

  Username: guest
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