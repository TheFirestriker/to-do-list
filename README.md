# To-Do-Liste (Engineering verteilter Anwendungen - Hausaufgabe 1)

Dieses Java-Projekt wurde als Studienleistung im Rahmen des Moduls Engineering verteilter Anwendungen an der TU Berlin entwickelt. 
Es handelt sich um eine konsolenbasierte Anwendung zur strukturierten Verwaltung, Filterung und Priorisierung von täglichen Aufgaben (To-Dos).

## 🚀 Features & Funktionalitäten

Die Anwendung bietet ein interaktives Hauptmenü auf der Konsole mit folgenden Funktionen:
1. **Persistente Datenspeicherung (File-I/O):** Alle Aufgaben werden aus einer `.txt`-Datei importiert und bei jeder Änderung automatisch dorthin zurückgeschrieben.
2. **Umfassende Listenansicht:** Getrennte Ausgabe aller registrierten Aufgaben oder filterbar nach ausschließlich offenen (ungecheckten) To-Dos.
3. **Dynamische Suche & Prädikate:** Flexibles Durchsuchen der To-Dos nach Textinhalten oder spezifischen Tags/Attributen.
4. **Statusverwaltung:** Einfaches Umschalten des Erledigungsstatus (`( )` zu `(x)`) mit automatischer Erfassung des Erledigungsdatums.
5. **Massenänderungen (Bulk Operations):** Suchen nach bestimmten Attributen (z. B. "AS") und gleichzeitiges Ändern der Priorität für alle Treffer.
6. **Erstellung neuer To-Dos:** Komfortabler Assistent zum Erstellen neuer Aufgaben inklusive Priorität und optionalen Zusatz-Attributen.

## 🛠️ Technische Umsetzung & Software-Konzepte

Bei der Softwarearchitektur wurde großer Wert auf Clean Code und Modularität gelegt:
- **Objektorientierung (OOP):** Kapselung aller To-Do-Eigenschaften wie Erstellungsdatum (`LocalDate`), Priorität, Status und flexiblen String-Arrays für Attribute.
- **Entwurfsmuster / Funktionales Interface:** Verwendung des eigenen `ToDoPredicate`-Interfaces (vergleichbar mit Javas `java.util.function.Predicate`), um Suchkriterien (`SearchByContent`, `SearchByAttribute`, `SearchByPriority`) sauber voneinander zu trennen und dynamisch zu entkoppeln.
- **Robustheit (Exception Handling):** Absicherung der Benutzereingaben im Menü gegen fehlerhafte Eingaben mittels `try-catch`-Blöcken für `NumberFormatException`, `InputMismatchException` und `IndexOutOfBoundsException`.
- **Daten-Parsing:** Eigenständiges Parsen von strukturierten Textzeilen aus der Speicherdatei über präzise String-Substrings.

## 💻 Technologie-Stack

- **Programmiersprache:** Java (JDK 25 kompiliert)
- **Build-Management:** Maven (vorgegebenes `pom.xml` Setup)
- **Bibliotheken:** - [Lombok](https://projectlombok.org/) (zur automatischen Generierung von Gettern, Settern, Loggern und Konstruktoren via `@Getter`, `@Setter`, `@Log`, `@AllArgsConstructor`)
  - JUnit Jupiter (vorbereitet für Unit-Tests)

## 📦 Projektstruktur

```text
├── src/
│   └── main/
│       ├── java/
│       │   ├── ToDo.java               # Datenmodell für ein To-Do-Element
│       │   ├── ToDoPredicate.java      # Funktionales Interface für Suchfilter
│       │   ├── ToDoService.java        # Kernlogik (Laden, Speichern, Verarbeiten)
│       │   ├── SearchByAttribute.java  # Filter-Logik für Tags/Attribute
│       │   ├── SearchByContent.java    # Filter-Logik für Textinhalte
│       │   ├── SearchByPriority.java   # Filter-Logik für Prioritäten
│       │   └── Main.java               # Konsolenmenü und App-Start
│       └── Todo.txt                    # Lokale Speicherdatei der Anwendung
├── pom.xml                             # Maven Konfigurationsdatei
└── .gitignore                          # Git-Ausschlussregeln für IDEs (IntelliJ, Eclipse, VS Code)
