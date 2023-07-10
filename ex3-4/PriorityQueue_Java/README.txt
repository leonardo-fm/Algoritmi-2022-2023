****COMPILAZIONE***

----PER COMPILARE LE CLASSI PER LA STRUTTURA DATI PriorityQueue NEL PACKAGE priorityqueue---
1) posizionarsi in .../PriorityQueue_Java/src
2) javac -d ../classes priorityqueue/PriorityQueue.java

---PER COMPILARE LE CLASSI PER GLI UNIT TEST NEL PACKAGE priorityqueue---
1) posizionarsi in .../PriorityQueue_Java/src
2) javac -d ../classes -cp '.;../junit-4.12.jar;../hamcrest-core-1.3.jar' priorityqueue/*.java (ATTENZIONE: i classpath devono essere separati da ";" come in Windows, non da ":" come in Unix, inoltre, occorre mettere l'elenco dei classpath fra apici semplici!)


***ESECUZIONE***

---PER ESEGUIRE priorityqueue/PriorityQueueJavaTestsRunner---
1) posizionarsi in .../PriorityQueue_Java/classes 
2) java -cp '.;../junit-4.12.jar;../hamcrest-core-1.3.jar'  priorityqueue/PriorityQueueJavaTestsRunner

