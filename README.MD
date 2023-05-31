**Multilanguage Comments extractor**

This tool allows the automatic extraction of source code comments from software projects, and their characterization in terms of length, number of lines, and position in the file.

*How the extractor works.*
Once the path of the folder containing the repository has been assigned, the program recursively enters all the subdirectories and whenever it finds a code file (recognized through a list of extensions), the file is read and the comments are extrapolated based on to the types of syntax of language comments.

Whenever a file with a specific extension is found, the program reads the file line by line and character by character, and when it finds a comment based on the syntax of the language, it inserts the comment into a structured list.

After reading the source file, the list contains all extracted comments, and for each comment contains information about the line number, column number and length of the comment.

Once this operation is finished, a CSV file is created with the same name as the source file to which the ".csv" extension is added. It contains all the comments extrapolated from the file and some relevant information such as the position of the comment within the file. The newly created file will be placed in a specific folder.

Languages supported for extraction:
c++, python,go, c, javascript, solidity,typescript,java,rust,cue,css,haskell, markdown, ruby,



**Italian**
Questo strumento consente l'estrazione automatica dei commenti del codice sorgente dai progetti software, e la loro caratterizzazione in termini di lunghezza, numero di righe e posizione nel file.

*Funzionamento dell’estrattore.*

Una volta assegnato il percorso della cartella contenente la repository, il programma entra in maniera ricorsiva in tutte le sottodirectory e ogni qual volta trova un file di codice (riconosciuto tramite una lista di estensioni), il file viene letto e vengono estrapolati i commenti in base alle tipologie di sintassi dei commenti del linguaggio.

Ogni volta che viene trovato un file con estensione specifica, il programma legge il file riga per riga e carattere per carattere, e quando trova un commento basato sulla sintassi del linguaggio, inserisce il commento all'interno di una lista.

Dopo aver letto il file sorgente, la lista contiene tutti i commenti estratti, e per ogni commento contiene le informazioni sul numero di riga, colonna e lunghezza del commento.

Una volta che i commenti sono stati estrapolati, questi  vengono  inseriti all’interno di una lista.  Una volta finita tale operazione viene creato un file CSV con lo stesso nome del file sorgente al quale viene aggiunta l’estensione “.csv”. Al suo interno vengono riportati tutti i commenti estrapolati del file  e alcune informazioni rilevanti quali la posizione del commento all’interno del file. Il file appena creato verrà inserito in una cartella apposita  .

Linguaggi supportati per l'estrazione: 
c++, python,go, c, javascript, solidity,typescript,java,rust,cue,css,haskell, markdown, ruby,

In questa repositori è compreso un altro progetto per la classificazione del testo attraverso lo stanford classifier .
per il prop e dataset contattatemi.



tesi sul progetto:


https://docs.google.com/document/d/1EXGS0nGLS3JGIIsR-EF9bfKrk5-IEBxzkOjpGPYyI34/edit?usp=sharing
