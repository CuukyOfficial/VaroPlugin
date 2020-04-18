# Contributing
## German 

Richtilinien zum Programmieren:
- **Ausschließlich** objektorientierte Programmierung (Ausnahme: Utils)
- Comments, Variablen, Methoden, System-outs, ... (Alles, was nicht an die user gerichtet ist) auf **Englisch**
- Camel Case-Notation
- Keine Fehler im Code nach Wechseln der Bukkit oder Spigot API (Reflections)
- Variablen nutzen, keine Config-Zugriffe während des Spielens
- Leerzeilen möglichst vermeiden -> Zeilen sinngemäß anordnen
- Keine Initialisierung der Variablen (außer ggf. final) in der Deklarierung
- Fields mit Annotations vor allen anderen mit Leerzeile
- Getter-Setter unten und nacheinander - Methoden sinngemäß und nach reihenfolge sortieren
- Sortierung nach Sichtbarkeit
  <ol style="list-style-type:square;">
    <li>Private</li>
    <li>Protected</li>
    <li>Package</li>
    <li>Public</li>
  </ol>
- Sortierung der Methoden:
  <ol style="list-style-type:square;">
    <li>Types</li>
    <li>Static Fields</li>
    <li>Static Intializers</li>
    <li>Fields</li>
    <li>Initializers</li>
    <li>Constructors</li>
    <li>Methods</li>
    <li>Static Methods</li>
  </ol>

<br>

**Richtlinien für GitHub:**
- Commits auf Englisch
- **Niemals** auf den master pushen
- Commit-Nachrichten kurz, sinngemäß und effizient beschreiben

**Bei Missachtung der Richtlinien werden pushes verweigert und Rechte entzogen**

## English

Guidelines for programming:
- **Exclusively** object-oriented programming (exception: Utilities)
- Comments, variables, methods, system-outs, ... (Everything that is not addressed to the user) in **English**
- camel case notation
- No errors in code after changing the Bukkit or Spigot API (Reflections)
- use variables, no config accesses during the game
- Avoid blank lines if possible -> arrange lines accordingly
- No initialization of the variables (except final) in the declaration
- Fields with annotations before all others with blank line
- Getter-setters below and one after the other - Sort the methods in the same order
- Sorting by visibility
  <ol style="list-style-type:square;">
    <li>Private</li>
    <li>Protected</li>
    <li>Package</li>
    <li>public</li>
  </ol>
- Sorting the methods:
  <ol style="list-style-type:square;">
    <li>types</li>
    <li>Static fields</li>
    <li>Static Intializers</li>
    <li>Fields</li>
    <li>Initializers</li>
    <li>Constructors</li>
    <li>Methods</li>
    <li>Static Methods</li>
  </ol>

<br>

**Guidelines for GitHub:**
- Commits in English
- **Never** push on the master
- Describe commit messages briefly, meaningfully and efficiently

**Disregard of the guidelines will result in denial of pushes and withdrawal of rights**
