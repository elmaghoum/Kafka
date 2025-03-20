.PHONY: build prod cons

# La cible build effectue le nettoyage, l'installation et la compilation
build:
	clear
	mvn clean install
	mvn compile

# La cible prod dépend de build puis lance le producteur
prod: build
	mvn exec:java -Dexec.mainClass="MADM.kafka.ProdMain"

# La cible cons dépend de build puis lance le consommateur
cons: build
	mvn exec:java -Dexec.mainClass="MADM.kafka.ConsMain"

