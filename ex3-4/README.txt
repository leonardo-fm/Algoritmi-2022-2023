****COMPILAZIONE***

----PER COMPILARE LE CLASSI PER LA STRUTTURA DATI PriorityQueue NEL PACKAGE priorityqueue---
1) posizionarsi in .../src
2) javac -d ../bin priorityqueue/PriorityQueue.java

---PER COMPILARE LE CLASSI PER GLI UNIT TEST NEL PACKAGE priorityqueue---
1) posizionarsi in .../src
2) javac -d ../bin -cp '.;../junit-4.12.jar;../hamcrest-core-1.3.jar' priorityqueue/*.java


----PER COMPILARE LE CLASSI PER LA STRUTTURA DATI Graph NEL PACKAGE graph---
1) posizionarsi in .../src
2) javac -d ../bin graph/Graph.java

---PER COMPILARE LE CLASSI PER GLI UNIT TEST NEL PACKAGE graph---
1) posizionarsi in .../src
2) javac -d ../bin -cp '.;../junit-4.12.jar;../hamcrest-core-1.3.jar' graph/*.java


----PER COMPILARE L'ALGORITMO DI PRIM---
1) posizionarsi in .../src
2) javac -d ../bin Prim.java

***ESECUZIONE***

---PER ESEGUIRE priorityqueue/PriorityQueueJavaTestsRunner---
1) posizionarsi in .../bin
2) java -cp '.;../junit-4.12.jar;../hamcrest-core-1.3.jar'  priorityqueue/PriorityQueueJavaTestsRunner


---PER ESEGUIRE graph/GraphJavaTestsRunner---
1) posizionarsi in .../bin
2) java -cp '.;../junit-4.12.jar;../hamcrest-core-1.3.jar'  graph/GraphJavaTestsRunner


---PER ESEGUIRE Prim---
1) posizionarsi in .../bin
2) java Prim "../data/italian_dist_graph.csv"
