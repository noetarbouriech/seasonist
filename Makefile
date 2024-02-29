dependencies:
	cd users/ && quarkus build
	cd companies/ && quarkus build
	docker compose build
	cd front/ && npm install
