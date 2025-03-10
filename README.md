-Premièrement il faut démarrer kafka (zookeeper + kafka)
-Créer 2 sujets : bank-account-input et bank-account-output en limitant chaque topic sur une partition

 kafka-topics.sh --create -topic bank-account-input -bootstrap-server localhost:9092 -partitions 1 -replication-factor 1

kafka-topics.sh --create -topic bank-account-output -bootstrap-server localhost:9092 -partitions 1 -replication-factor 1

-Créer un producteur sur la topic : bank-account-input
-Créer un consommateur sur le sujet : bank-account-output
-Démarrer l’application

-Faire un test avec postman:
