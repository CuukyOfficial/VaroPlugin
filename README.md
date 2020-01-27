## Varo Plugin by Cuuky 
<img src="http://185.194.142.45/Bilder/Varo/thumbnail.png">

Hey, ich habe viel an dem Plugin gearbeitet, es würde mich freuen, wenn du mir die Credits dafür lassen könntest.
Das Plugin läuft universal auf den Versionen 1.7 - 1.15.

**Libraries:** 
- http://www.mediafire.com/file/e6xl995rgt5zvmr/varo_lib.zip/file
- https://getbukkit.org/download/spigot

## Formale Richtlinien
Richtilinien zum Programmieren:
- **Ausschließlich** objektorientierte Programmierung (Ausnahme: Utils)
- Comments, Variablen, Methoden, System-outs, ... (Alles, was nicht an die user gerichtet ist) auf **Englisch**
- Camel Case-Notation
- Keine Fehler im Code nach Wechseln der Bukkit oder Spigot API (Reflections)
- Variablen nutzen, keine Config-Zugriffe während des Spielens
- Leerzeilen möglichst vermeiden -> Sinngemäß anordnen
- Keine Initialisierung der Variablen (außer ggf. final) in der Deklarierung
- Die unten vorgegebene Sortierung nutzen
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

##

Spigot-Seite mit Docs etc.: https://www.spigotmc.org/resources/71075/

Vielen Dank & happy coding!
