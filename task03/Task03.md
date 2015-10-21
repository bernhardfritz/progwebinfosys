# Task 1:

Abgabedatum - 26.10.2015 bis 12:00 Uhr

## Doing

Bitte beachten, dass im Rahmen des "MyBlog"-Systems die Verwendung von Frameworks/Libraries (egal ob HTML, CSS, PHP, ...) nicht erlaubt ist!

### MyBlog - Kommentare erstellen
Erlaube das Erstellen von Kommentaren zu einem Blogeintrag.   
Verwende zum Speichern eines Kommentares AJAX (kein neuer Seitenaufbau).

### MyBlog - HTML bzw. BBCode in Beiträgen/Kommentaren
Blogeinträge als reiner Text sind doch etwas eingeschränkt.   
Setze daher einen Mechanismus um, mit welchem der Text strukturiert werden kann.   
Dabei sollen mindestens folgende Dinge ermöglicht werden:
 - Verwendung von Links
 - Text-Eigenschaften: Schriftgröße, Textart (fett, unterstrichen)
 - Verwenden von Überschriften (h1, h2, h3)
Überlege, an welcher Stelle HTML- bzw BBCode-Tags übersetzt werden sollen (Server und/oder Client) und warum dies genau dort sinnvoll ist.
   
BTW:   
Weitere Elemente (etwa das Einbinden von Bildern) ist optional und nicht zwingend erforderlich

### MyBlog - Benutzerauthentifizierung
Momentan gibt es nur einen einzigen "globalen" Benutzer, der im MyBlog-System alles machen kann.
Erstelle ein rudimentäres (!!) Benutzersystem, das mindestens vier unterschiedliche Benutzer kennt: admin, autor, user, guest.   
Dabei soll ein Admin alle Funktionen des Systems verwenden können (inkl. der Löschung von Beiträgen/Kommentaren), während ein autor Artikel (und Kommentare) verfassen können soll. Ein user darf nur Kommentare aber keine Beiträge erstellen, während ein guest nur lesenden Zugriff im System haben soll.   
Ist am System kein Benutzer eingeloggt, so soll per default die guest Rolle verwendet werden.   

Überlege welche verschiedenen Möglichkeiten es zur Umsetzung eines solchen Benutzersystems gibt (etwa: in PHP, direkt im Webserver, ...). Beachte dabei die jeweiligen Vor- bzw. Nachteile der unterschiedlichen Ansätze.

## Reading:

### PHP
[PHP Manual](http://php.net/manual/de)

### BBCode
[BBCode](https://de.wikipedia.org/wiki/BBCode)

### Benutzerauthentifizierung
[Apache Auth]https://httpd.apache.org/docs/2.4/howto/auth.html
[PHP Loginsystem](https://wiki.selfhtml.org/wiki/PHP/Anwendung_und_Praxis/Loginsystem)
