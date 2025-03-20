Pour compiler et exécuter, utiliser Maven, avec les commandes suivantes :

```
mvn clean install
mvn compile

mvn exec:java -Dexec.mainClass="MADM.kafka.ConsMain"
mvn exec:java -Dexec.mainClass="MADM.kafka.ProdMain"
ou

mvn exec:java

ou

make build
make prod
make cons


```


4) Avec 2 Prod 2 Cons / avec les consommateurs dans un même groupe
-> Tous les consomateur consomme/recoive les messages 
***TEMPS DE CONSOMATION  de 1000000 msg: ***
Cons1 :
====== Fin de la consommation courante ======
Messages consommés cette session : 500543
Durée réelle de cette session (s) : 7.854
---------------------------------------------
TOTAL cumulatif depuis le début : 
Messages totaux : 500543
Durée totale cumulée réelle (s) : 7.854
====================================


Cons2 :
====== Fin de la consommation courante ======
Messages consommés cette session : 499324
Durée réelle de cette session (s) : 7.856
---------------------------------------------
TOTAL cumulatif depuis le début : 
Messages totaux : 499324
Durée totale cumulée réelle (s) : 7.856
====================================



***TEMPS DE CONSOMATION  de 1000 msg: ***
Cons1 :


Cons2 :



Avec 2 Prod 3 Cons / avec les consommateurs dans un même groupe
-> Seule 2 consomateur consomme les messages. Cela est du a la partition qui est a 2.

***TEMPS DE CONSOMATION de 1000 ***
Cons1 :


Cons2 :


Cons3: ne recois rien


***TEMPS DE CONSOMATION de 1000000 ***

Cons1:


Cons2 :


Cons3: ne recois rien

4) 2 Prod 3 Cons / avec des consommateurs de différents groupes
-> Tout les Cons consomme les messages