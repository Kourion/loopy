//TestFields (KILLERKRITERIUM)  
//testNonFinalButStaticFields: Das Bestehen dieses Tests ist für eine erfolgreichen Bearbeitung der Aufgabe zwingend erforderlich.  

TestIntegration (insgesamt 40 Punkte)  

testLoadSave (2 Punkte): Lädt einige Spiele aus einer Datei und schreibt sie in eine andere Datei zurück. Die Dateien sollten danach inhaltlich identisch sein.  
testLoadRotateSave (5 Punkte): Lädt einige Spiele aus einer Datei. Rotiert einige Felder beliebig oft und speichert das Spiel dann wieder in einer Datei.   
Der Test prüft, ob die Rotationen korrekt in die gespeicherte Datei eingeflossen sind.  
--> Create binary writer.  

testLoadIllegal (3 Punkte): Lädt einige fehlerhafte Dateien. Der Test prüft, ob eine Exception geworfen wurde und falls ja welche.  
--> Throw Exception, add gui feedback.   

testLoadSolveSaveSolvable (5 Punkte): Lädt einige lösbare Spiele aus einer Datei, lässt sie lösen und speichert sie wieder.  
Der Test prüft, ob solve true zurückgegeben hat, das Feld unzulässig geändert wurde (z.B. Kacheln verändert und nicht nur gedreht wurden) und ob das gespeicherte Feld auch wirklich gelöst ist.  
testLoadSolveUnsolvable (5 Punkte): Lädt einige nicht lösbare Spiele aus einer Datei und lässt sie lösen. Der Test prüft, ob erkannt wurde, dass das Spiel nicht lösbar ist.  
--> Create solve algorithm.  

testGenerateSolveSave (10 Punkte): Generiert einige Spiele, lässt sie lösen und speichert sie. Dabei wird geprüft, ob  
das Feld innerhalb der Größenbeschränkungen des Generators liegt  
das Feld lösbar war  
das Feld korrekt gelöst wurde  
das Feld kein Duplikat eines der vorherigen Felder ist  
die erstellten Felder in ihrer Größe gleich verteilt sind.  
testGenerateSaveDistribution (10 Punkte): Generiert einige Spiele und speichert sie.   
Danach wird die Häufigkeit der unterschiedlichen Kacheltypen auf den Feldern berechnet und mit der erwarteten Verteilung eines ideal gleichverteilten Generators verglichen.   
Überschreitet die Abweichung ein bestimmtes Maß, schlägt der Test fehl.  
--> create gamefield generator.  

Menü (insgesamt 15 Punkte)  
//Laden (3 Punkte): Eine Spieldatei im oben definierten Format soll durch den Nutzer über ein GUI Element komfortabel gewählt und geladen werden können.   
//Das geladene Feld wird angezeigt und kann gespielt werden.   
Wird eine nicht korrekt formatierte Datei geladen, wird eine Ausnahmebehandlung durchgeführt und dem Nutzer über die GUI Rückmeldung gegeben.  
Speichern (2 Punkte): Das aktuelle Feld wird in seinem aktuellen Zustand vollständig und korrekt in eine vom Nutzer komfortabel über ein GUI Element ausgewählte Datei geschrieben.   
Die Datei wird im Zweifel neu erstellt.  
Shuffle (4 Punkte): Das Programm aktiviert einen Spielmodus in dem zufällige (auch zufällig große!), lösbare Felder automatisch erstellt werden und gespielt werden können.   
Die Felder sind so verdreht, dass der Nutzer in nicht trivialer Weise den korrekten Zustand des Feldes erspielen kann. Die Felder sind im Durchschnitt mindestens 10x10 groß und es werden nicht immer gleich große Felder erzeugt.  
Solve (4 Punkte): Das aktuelle Spielfeld wird von einem Algorithmus korrekt gelöst und in gelöstem Zustand angezeigt. Der Nutzer kann auf Wunsch mit dem nächsten Spiel fortfahren.  
Sonstiges (2 Punkte): Aus Tooltips oder Beschriftungen ist ersichtlich, welches GUI Element welchem Zweck dient.  
Das Menü ist insgesamt in angenehmer Weise bedienbar und hat eine ästhetische und sinnvolle Größe, Aufteilung und Anordnung der einzelnen Elemente.  

Spielfeld (insgesamt 45 Punkte)  
//Skalierung (insgesamt 10 Punkte)  
//Größe (5 Punkte): Das Programm bietet einen Vollbild- sowie einen Fenstermodus mit anpassbarer Fenstergröße.  
//Das Spielfeld ist fensterfüllend (evtl. abzüglich Menü). Horizontale bzw. vertikale Seitenränder sind erlaubt, damit die einzelnen Kacheln quadratisch bleiben.  
//Dynamische Skalierung (5 Punkte): Der Inhalt des Spielfeldes passt sich dynamisch der Fenstergröße an, um den vorhandenen Platz optimal auszunutzen.  
//Dies gilt insbesondere für Änderung der Fenstergröße und Laden bzw. Erstellen neuer Felder.  
//Grafiken (5 Punkte): Die verwendeten Grafiken spiegeln Art und Orientierung des Feldes wieder.   
//Es wird nicht für jedes Level dieselbe Optik verwendet (Setzen Sie z.B. Farbvariationen oder Ähnliches um).  
//Die Grafiken gehen nahtlos ineinander über und visualisieren so das Spielkonzept ununterbrochener, endloser Schlaufen.  
//Interaktionen (10 Punkte): Die einzelnen Kacheln lassen sich anklicken. Ein Klick sorgt für die entsprechende Rotation der richtigen Kachel in der internen Datenhaltung.  
//Es wird korrekt erkannt, wenn ein Spiel gelöst wurde und dem Nutzer wird darüber Feedback gegeben.   
Animationen (insgesamt 20 Punkte)   
//Drehungen (8 Punkte): Kacheln, mit denen interagiert wurde, drehen sich animiert entsprechend des Spielgeschehens.   
//Wechsel (4 Punkte): Sobald ein Feld gelöst, wird ändert sich die Optik des Feldes. Gleiches gilt, wenn ein neues Feld geladen wird.   
Solve (8 Punkte): Das ungelöste Feld wird Kachel für Kachel gelöst. Die Kacheln werden schrittweise animiert in die richtigen Positionen gedreht.  

/********************************************************/  

Add completeChecker for logicTiles  
Add random board creator