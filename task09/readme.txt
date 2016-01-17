Vorbereitung:
	Ausführen des Befehls "npm install" im root-Verzeichnis, um die dependencies zu installieren.
	Eintragen der Neo4j - User-Passwort-Kombination in 'controllers/dbManager.js' in Zeile 6.
  Starten des Servers mit "node server.js" im root-Verzeichnis.
  Server erreichbar unter 'localhost:8080'.

Info:
  Beim Serverstart werden 10 Personen mit jeweils einer Adresse in Innsbruck und der zugehörigen Relation in die Neo4j-Datenbank gespeichert.
  Die entsprechenden Koordinaten für alle Adressen werden beim Laden der Seite abgefragt und als Marker in die Karte eingezeichnet.
  Als Benutzername im Chat wird die jeweilige IP-Adresse des Clients verwendet.
