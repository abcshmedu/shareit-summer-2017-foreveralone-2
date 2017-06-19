# ShareIt Anwendung

Von Simon Weidacher
Mat# 14492913

## Wichtige Hinweise

* Der Authentication-Service wurde nicht als "klassischer" Microservice implementiert, sondern nur als eigenständige Ressource.
Um eine Migration zum Microservice vorzubereiten greift diese als einzige auf das Modell `Account` zu (und sonst auf keine andere Tabelle).
Ausserdem findet die Kommunikation zu anderen Services ausschließlich über HTTP-Requests statt.
* Es wurden keine Mockobjekte zum Testen generiert. Statessen habe ich meine simplen Datenhaltungsstrategien aus der ersten Abgabe bei den betreffenden
Tests injiziert.

## Deployment

Heroku URL: https://shareit-softarch.herokuapp.com/shareit

## Modelle

Alle mit IMMUTABLE gekennzeichneten Felder können nicht geändert werden. Alle Werte unter diesen Feldern werden ignoriert.

### Buch
```javascript
{
	"author" : "Astrid Lindgren", // Author des Buches
	"isbn" : "sjfi543f838200fi", // ISBN des Buches, normalisiert (ohne Bindestriche)
	"title" : "Wir Kinder vom Bahnhof Zoo", // Titel des Buches
	"lastUpdate" : "May 1, 2017 10:32:53 AM", // Zeit der letzten Änderung - IMMUTABLE
	"id" : "290a7943-8306-4b26-b004-68cf1f9b15ee", // interner identifier - IMMUTABLE
	"creationDate" : "May 1, 2017 10:32:53 AM" // Zeit des einstellen des Buches - IMMUTABLE
}
```

### Disc
```javascript
{
	"director" : "Astrid Lindgren", // Author des DVD
	"barcode" : "sjfi543f838200fi", // ISBN des DVD
	"title" : "Wir Kinder vom Bahnhof Zoo", // Titel der DVD
	"lastUpdate" : "May 1, 2017 10:32:53 AM", // Zeit der letzten Änderung - IMMUTABLE
	"id" : "290a7943-8306-4b26-b004-68cf1f9b15ee", // interner identifier - IMMUTABLE
	"creationDate" : "May 1, 2017 10:32:53 AM" // Zeit des einstellen des Buches - IMMUTABLE
}
```

### Copy
```javascript
{
	"class" : "Book", // Ordnung der Kopie. Kann entweder "Book" oder "Disc" sein
	"isbn" : "sjfi543f838200fi", // ISBN des Buches dieser Kopie. Ignoriert, falls "class" != "Book"
	"barcode" : "sss111wf1ff", // Barcode des Buches dieser Kopie. Ignoriert, falls "class" != "Disc"
	"owner" : "tuberains", // Benutzername des Besitzers dieser Kopie
	"lastUpdate" : "May 1, 2017 10:32:53 AM", // Zeit der letzten Änderung - IMMUTABLE
	"id" : "290a7943-8306-4b26-b004-68cf1f9b15ee", // interner identifier - IMMUTABLE
	"creationDate" : "May 1, 2017 10:32:53 AM" // Zeit des einstellen des Buches - IMMUTABLE
}
```

## API Beschreibung - Anlegen von Exemplaren

Alle Pfade haben den Präfix: `/shareit/copys`
Für Copy und Kopie wird synonym zu Exemplar verwendet.

### Bücher

|Pfad|Methode|Parameter|Antwort|Beschreibung|
|----|-------|------------|---------|------------|
|`/books`|GET| - |200 OK - `[Buch]`|<ul><li>Alle Exemplare aller Bücher abfragen</li></ul>|
|`/books/{isbn}`|POST|body: `Copy`|<ul><li>201 CREATED + Location Header</li><li>400 BAD REQUEST - Modell der Copy fehlerhaft</li></ul>|Ein Exemplar anlegen|
|`/books/{isbn}`|GET|`isbn` - ISBN|<ul><li>200 OK - `[Buch]`</li><li>404 NOT FOUND - Kein Buch unter der ISBN angelegt </li><li>400 BAD REQUEST - ISBN syntaktisch ungültig</li></ul>|Alle Exemplare eines Buches abfragen|
|`/books/{isbn}/{id}`|GET|<ul><li>`isbn`: ISBN des Buches</li><li>`id` : identifier der Kopie</li></ul>|<ul><li>200 OK - `Buch`</li><li>400 BAD REQUEST - ISBN oder ID syntaktisch inkorrekt</li><li>404 NOT FOUND - Kein Buch unter `isbn` oder keine Kopie für das Buch unter `isbn` unter `id`</li></ul>|Eine spezielle Kopie eines Buches abfragen.|
|`/books/{isbn}/{id}`|PUT|body: `Copy`|<ul><li>200 OK - URL zur Copy</li><li>400 BAD REQUEST - ISBN, ID oder Copy syntaktisch inkorrekt.</li><li>404 NOT FOUND - kein Buch/Copy unter ISBN/ID gelistet</li></ul>|Eine spezielle Kopie verändern|


### Discs

|Pfad|Methode|Parameter|Antwort|Beschreibung|
|----|-------|------------|---------|------------|
|`/discs`|GET| - |200 OK - `[Disc]`|Alle Exemplare aller Discs abfragen|
|`/discs/{barcode}`|POST|body: `Copy`|<ul><li>201 CREATED + Location Header</li><li>400 BAD REQUEST - Modell der Copy fehlerhaft</li></ul>|Ein Exemplar anlegen|
|`/discs/{barcode}`|GET|`barcode` - Barcode|<ul><li>200 OK - `[Disc]`</li><li>404 NOT FOUND - Keine Disc unter der ISBN angelegt </li><li>400 BAD REQUEST - ISBN syntaktisch ungültig</li></ul>|Alle Exemplare eines Buches abfragen|
|`/discs/{barcode}/{id}`|GET|<ul><li>`barcode`: Barcode der Disc</li><li>`id` : identifier der Kopie</li></ul>|<ul><li>200 OK - `Disc`</li><li>400 BAD REQUEST - Barcode oder ID syntaktisch inkorrekt</li><li>404 NOT FOUND - Kein Buch unter `isbn` oder keine Kopie für das Buch unter `isbn` unter `id`</li></ul>|Eine spezielle Kopie eines Buches abfragen.|
|`/discs/{barcode}/{id}`|PUT|body: `Copy`|<ul><li>200 OK - URL zur Copy</li><li>400 BAD REQUEST - Barcode, ID oder Copy syntaktisch inkorrekt.</li><li>404 NOT FOUND - kein Disc/Copy unter ISBN/ID gelistet</li></ul>|Eine spezielle Kopie verändern|
