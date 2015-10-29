# Task 4:

Abgabedatum - 02.11.2015 bis 12:00 Uhr

## Doing

Bitte beachten, dass im Rahmen des "MyBlog"-Systems die Verwendung von Frameworks/Libraries (egal ob HTML, CSS, PHP, ...) nicht erlaubt ist!

### MyBlog - REST-API
Die grundlegenden Funktionen des MyBlog Systems sollen über eine REST-API verfügbar gemacht werden.   
Stelle deshalb folgende Funktionalitäten per REST zur Verfügung:
 - Abrufen aller Blog-Einträge
 - Anlegen eines Blog-Eintrags
 - Löschen eines Blog-Eintrags
 - Verändern eines Blog-Eintrags
 - Abrufen aller Kommentare zu einem Blog-Eintrag
 - Anlegen eines neuen Kommentars zu einem Blog-Eintrag
 - Löschen eines Kommentares
 - Verändern eines Kommentars
Überlege bei jeder Funktionalität, wie diese vor unerwünschtem Zugriff geschützt werden kann (siehe Benutzerauthentifizierung aus Task3) und wie diese technisch gut umgesetzt werden kann (etwa: welche HTTP-Methode verwendet werden sollte). Finde sinnvolle REST-Endpoints und kommentiere die einzelnen Funktionen (etwa: welche Werte müssen in welcher Form übergeben werden? Was ist das eindeutige Feld, welches beim Löschen eines Beitrags verwendet wird...)

### MyBlog - Installation und Anbindung an eine MySQL/MariaDB-Datenbank
Installiere MySQL oder MariaDB und konfiguriere es so, dass PHP darauf zugreifen kann.   
Verwende zur Herstellung einer DB-Verbindung einen passenden Treiber und begründe, warum gerade dieser sinnvoll ist.   
Sichere die Verbindung zwischen PHP und der Datenbank mit Benutzername und Passwort ab (Benutzername "root" und Passwort "" sind nicht erwünscht).

### MyBlog - Speicherung der Daten in einer MySQL/MariaDB-Datenbank
Bisher werden die Daten des Blog-Systems in einer HTTP-Session gespeichert.   
Wie schon in der VO erwähnt ist dies gerade für größere Datenmengen nicht optimal.   
Speichere deshalb die Daten in eine relationale MySQL/MariaDB-Datenbank ab und finde ein passendes DB-Schema.

## Reading:

### PHP
[Installation von MySQL](https://www.howtoforge.de/anleitung/ubuntu-14.04-linux-apache-mysql-php-lamp/)
[REST](http://www.ics.uci.edu/~fielding/pubs/dissertation/top.htm)
[REST API-Design](http://roy.gbiv.com/untangled/2008/rest-apis-must-be-hypertext-driven)

