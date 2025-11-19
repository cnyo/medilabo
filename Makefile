PROJECT=medilabo

.PHONY: build start stop restart deploy logs prune

build:
	docker compose build

start:
	docker commpose up -d

stop:
	docker compose down

restart:
	docker compose down
	docker commpose up -d

deploy:
	docker compose pull
	docker compose -d

logs:
	docker compose logs -f

prune:
	docker system prune -f