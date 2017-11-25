SHELL := /bin/bash
PROJECT := $(shell basename $$(pwd))



# Bring all APIs UP
up:
	clear && \
	docker-compose up --build --force-recreate --remove-orphans

# Stop all API stack
down:
	clear && \
	docker-compose down --remove-orphans



# Tests
# Test all with Docker
test:
	clear && \
	docker-compose -f ./docker-compose-test.yml up --build --remove-orphans

# Load Tests - Campaign
load-tests-campaign:
	clear && \
	cd load_tests && \
	./gradlew runCampaignSimulation && \
	cd ../

# Load Tests - Associate Fan
load-tests-fan:
	clear && \
	cd load_tests && \
	./gradlew runFanSimulation && \
	cd ../

# end-to-end tests
e2e:
	clear && \
	cd e2e_tests && \
	./gradlew check && \
	cd ../



# Stream Task
# HOW TO: make stream input=aAbBABacafe OR make stream-docker input=aAbBABacafe
stream:
	clear && \
	cd ./stream && \
	./gradlew build && \
	java -jar ./build/libs/com.netsocks.stream-1.0.0.jar $(input) && \
	cd ../

stream-docker:
	clear && \
	cd ./stream && \
	docker build -t netsocks.stream . && \
	docker run -e "INPUT=$(input)" --rm --name netsocks.stream -t netsocks.stream && \
	cd ../



# Campaign Task
campaign-api:
	clear && \
	cd campaign && \
	./gradlew build && \
	java -jar ./build/libs/campaign-1.0-SNAPSHOT.jar

campaign-api-docker:
	clear && \
	cd campaign && \
	docker build -t netsocks.campaign . && \
	docker run --rm --name netsocks.campaign -t netsocks.campaign



# Associate Fan Task
fan-api:
	clear && \
	cd associate_fan && \
	./gradlew build && \
	java -jar ./build/libs/associatefan-1.0-SNAPSHOT.jar

fan-api-docker:
	clear && \
	cd associate_fan && \
	docker build -t netsocks.associatefan . && \
	docker run --rm --name netsocks.associatefan -t netsocks.associatefan

.PHONY: up
