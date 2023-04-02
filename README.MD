Il funzionamento dell’estrattore è il seguente: 

Una volta assegnato il percorso della cartella contenente la repository, il programma entra in maniera ricorsiva in tutte le sottodirectory e ogni qual volta trova un file di codice (riconosciuto tramite una lista di estensioni), il file viene letto e vengono estrapolati i commenti in base alle tipologie di sintassi dei commenti del linguaggio.

Ogni volta che viene trovato un file con estensione specifica, il programma legge il file riga per riga e carattere per carattere, e quando trova un commento basato sulla sintassi del linguaggio, inserisce il commento all'interno di una lista.

Dopo aver letto il file sorgente, la lista contiene tutti i commenti estratti, e per ogni commento contiene le informazioni sul numero di riga, colonna e lunghezza del commento.

Una volta che i commenti sono stati estrapolati, questi  vengono  inseriti all’interno di una lista.  Una volta finita tale operazione viene creato un file CSV con lo stesso nome del file sorgente al quale viene aggiunta l’estensione “.csv”. Al suo interno vengono riportati tutti i commenti estrapolati del file  e alcune informazioni rilevanti quali la posizione del commento all’interno del file. Il file appena creato verrà inserito in una cartella apposita  .

Linguaggi supportati per l'estrazione: 
c++, python,go, c, javascript, solidity,typescript,java,rust,cue,css,haskell, markdown, ruby,

In questa repositori è compreso un altro progetto per la classificazione del testo attraverso lo stanford classifier .
Il prop da me utilizzato è il seguente:
#
 Features
#
#useClassFeature=true
1.useNGrams=false
1.useLowercaseSplitWords=true
1.splitWordsRegexp=\\s+
#useBinary=true
#1.binnedLengths=10,20,30
#
 Printing
#
printClassifier=HighWeight
printClassifierParam=60000
displayAllAnswers=true
printTo=top_features.txt
#
# Mapping
#
goldAnswerColumn=0
displayedColumn=1
#
 Optimization
#
intern=true
sigma=3
useQN=true
QNsize=15
tolerance=1e-4
#
Training input
#
trainFile=td_trainset.csv
testFile= td_testset.csv


https://docs.google.com/document/d/1EXGS0nGLS3JGIIsR-EF9bfKrk5-IEBxzkOjpGPYyI34/edit?usp=sharing
