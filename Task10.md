# Task 10:

Abgabedatum - 25.01.2016 bis 20:00 Uhr

## Doing

Bitte beachten, dass der Einsatz von Libraries/Frameworks erlaubt (und sogar erwünscht) ist.   

Vorweg: Im weiteren finden sich durachaus schwammige Anforderungen, die so nicht unbedingt
gestellt werden sollten. Da dies aber in der Praxis (zu) oft genau so gemacht wird, finden sich
solch unklare bzw. unvollständige Angaben auch hier.

### Finanzverwaltung

Ziel dieser Anwendung ist es, eine web-basierte Applikation zu entwickeln, die zur Verwaltung
von Finanzen dient. Es sollen Transaktionen erfasst werden können, welche einem Konto (bzw.
Sparbuch, Kreditkarte) zugeordnet werden. Transaktionen können dabei den Ist-Stand eines Kontos
sowohl positiv (Einnahmen) als auch negativ (Ausgaben) beeinflussen.

#### Anforderungen

Folgenden Anforderungen sind mindestens umzusetzen:

- Die Applikation muss über ein Anmeldesystem verfügen.
- Alle Transaktionen und alle Konten müssen persisiert werden. Verlässt ein Benutzer die
Applikation und meldet sich später erneut an, so müssen alle Kontostände und alle Transaktionen
mit
- Export- bzw. Import-Funktionalität muss implementiert werden
- Die Applikation ist als Single-Page-Webapplikation zu implementieren
- Es ist eine strikte Trennung zwischen HTML, JS und CSS einzuhalten
- Als JS-Framework kann wahlweise Webpack und/oder ReactJS verwendet werden
- Die Web-Applikation muss alle gesetzlichen Anforderungen von Accessibility gerecht werden
- Es ist auf eine geeignete Usability zu achten
- Die umgesetzten Funktionalitäten sind ausreichend zu testen

#### Rahmenbedingungen

Die Webseite darf zum initialen Laden nicht mehr als 2 Sekunden benötigen.
Werden Informationen später nachgeladen, darf die Zeit zum Nachladen 500msec nicht überschreiten.
Sollte dies nicht möglich sein, so muss der Benutzer darüber in geeigneter Art und Weise (Spinner,
Ladebalken) informiert werden.   
Bei Unklarheiten bzgl. Anforderungen bzw. Rahmenbedingungen soll die WebInfoSys-Mailingliste zur
Klärung verwendet werden.

# Reading:

[Bootstrap](http://getbootstrap.com/)   
[Karma](https://karma-runner.github.io/0.13/index.html)   
[Mocha](https://mochajs.org/)   
[NodeJS](https://nodejs.org/en/)   
[PostgreSQL](http://www.postgresql.org/)   
[ReactJS](https://facebook.github.io/react/)   
[SpinJS](http://fgnass.github.io/spin.js/)   
[Webpack](https://webpack.github.io/docs/)   
