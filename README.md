//TestFields (KILLERKRITERIUM)  
//testNonFinalButStaticFields: Das Bestehen dieses Tests ist f�r eine erfolgreichen Bearbeitung der Aufgabe zwingend erforderlich.  

TestIntegration (insgesamt 40 Punkte)  

testLoadSave (2 Punkte): L�dt einige Spiele aus einer Datei und schreibt sie in eine andere Datei zur�ck. Die Dateien sollten danach inhaltlich identisch sein.  
testLoadRotateSave (5 Punkte): L�dt einige Spiele aus einer Datei. Rotiert einige Felder beliebig oft und speichert das Spiel dann wieder in einer Datei.   
Der Test pr�ft, ob die Rotationen korrekt in die gespeicherte Datei eingeflossen sind.  
--> Create binary writer.  

testLoadIllegal (3 Punkte): L�dt einige fehlerhafte Dateien. Der Test pr�ft, ob eine Exception geworfen wurde und falls ja welche.  
--> Throw Exception, add gui feedback.   

testLoadSolveSaveSolvable (5 Punkte): L�dt einige l�sbare Spiele aus einer Datei, l�sst sie l�sen und speichert sie wieder.  
Der Test pr�ft, ob solve true zur�ckgegeben hat, das Feld unzul�ssig ge�ndert wurde (z.B. Kacheln ver�ndert und nicht nur gedreht wurden) und ob das gespeicherte Feld auch wirklich gel�st ist.  
testLoadSolveUnsolvable (5 Punkte): L�dt einige nicht l�sbare Spiele aus einer Datei und l�sst sie l�sen. Der Test pr�ft, ob erkannt wurde, dass das Spiel nicht l�sbar ist.  
--> Create solve algorithm.  

testGenerateSolveSave (10 Punkte): Generiert einige Spiele, l�sst sie l�sen und speichert sie. Dabei wird gepr�ft, ob  
das Feld innerhalb der Gr��enbeschr�nkungen des Generators liegt  
das Feld l�sbar war  
das Feld korrekt gel�st wurde  
das Feld kein Duplikat eines der vorherigen Felder ist  
die erstellten Felder in ihrer Gr��e gleich verteilt sind.  
testGenerateSaveDistribution (10 Punkte): Generiert einige Spiele und speichert sie.   
Danach wird die H�ufigkeit der unterschiedlichen Kacheltypen auf den Feldern berechnet und mit der erwarteten Verteilung eines ideal gleichverteilten Generators verglichen.   
�berschreitet die Abweichung ein bestimmtes Ma�, schl�gt der Test fehl.  
--> create gamefield generator.  

Men� (insgesamt 15 Punkte)  
//Laden (3 Punkte): Eine Spieldatei im oben definierten Format soll durch den Nutzer �ber ein GUI Element komfortabel gew�hlt und geladen werden k�nnen.   
//Das geladene Feld wird angezeigt und kann gespielt werden.   
Wird eine nicht korrekt formatierte Datei geladen, wird eine Ausnahmebehandlung durchgef�hrt und dem Nutzer �ber die GUI R�ckmeldung gegeben.  
Speichern (2 Punkte): Das aktuelle Feld wird in seinem aktuellen Zustand vollst�ndig und korrekt in eine vom Nutzer komfortabel �ber ein GUI Element ausgew�hlte Datei geschrieben.   
Die Datei wird im Zweifel neu erstellt.  
Shuffle (4 Punkte): Das Programm aktiviert einen Spielmodus in dem zuf�llige (auch zuf�llig gro�e!), l�sbare Felder automatisch erstellt werden und gespielt werden k�nnen.   
Die Felder sind so verdreht, dass der Nutzer in nicht trivialer Weise den korrekten Zustand des Feldes erspielen kann. Die Felder sind im Durchschnitt mindestens 10x10 gro� und es werden nicht immer gleich gro�e Felder erzeugt.  
Solve (4 Punkte): Das aktuelle Spielfeld wird von einem Algorithmus korrekt gel�st und in gel�stem Zustand angezeigt. Der Nutzer kann auf Wunsch mit dem n�chsten Spiel fortfahren.  
Sonstiges (2 Punkte): Aus Tooltips oder Beschriftungen ist ersichtlich, welches GUI Element welchem Zweck dient.  
Das Men� ist insgesamt in angenehmer Weise bedienbar und hat eine �sthetische und sinnvolle Gr��e, Aufteilung und Anordnung der einzelnen Elemente.  

Spielfeld (insgesamt 45 Punkte)  
//Skalierung (insgesamt 10 Punkte)  
//Gr��e (5 Punkte): Das Programm bietet einen Vollbild- sowie einen Fenstermodus mit anpassbarer Fenstergr��e.  
//Das Spielfeld ist fensterf�llend (evtl. abz�glich Men�). Horizontale bzw. vertikale Seitenr�nder sind erlaubt, damit die einzelnen Kacheln quadratisch bleiben.  
//Dynamische Skalierung (5 Punkte): Der Inhalt des Spielfeldes passt sich dynamisch der Fenstergr��e an, um den vorhandenen Platz optimal auszunutzen.  
//Dies gilt insbesondere f�r �nderung der Fenstergr��e und Laden bzw. Erstellen neuer Felder.  
//Grafiken (5 Punkte): Die verwendeten Grafiken spiegeln Art und Orientierung des Feldes wieder.   
//Es wird nicht f�r jedes Level dieselbe Optik verwendet (Setzen Sie z.B. Farbvariationen oder �hnliches um).  
//Die Grafiken gehen nahtlos ineinander �ber und visualisieren so das Spielkonzept ununterbrochener, endloser Schlaufen.  
//Interaktionen (10 Punkte): Die einzelnen Kacheln lassen sich anklicken. Ein Klick sorgt f�r die entsprechende Rotation der richtigen Kachel in der internen Datenhaltung.  
//Es wird korrekt erkannt, wenn ein Spiel gel�st wurde und dem Nutzer wird dar�ber Feedback gegeben.   
Animationen (insgesamt 20 Punkte)   
//Drehungen (8 Punkte): Kacheln, mit denen interagiert wurde, drehen sich animiert entsprechend des Spielgeschehens.   
//Wechsel (4 Punkte): Sobald ein Feld gel�st, wird �ndert sich die Optik des Feldes. Gleiches gilt, wenn ein neues Feld geladen wird.   
Solve (8 Punkte): Das ungel�ste Feld wird Kachel f�r Kachel gel�st. Die Kacheln werden schrittweise animiert in die richtigen Positionen gedreht.  

/********************************************************/  

Add completeChecker for logicTiles  
Add random board creator