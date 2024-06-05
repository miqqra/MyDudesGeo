Инструкция по запуску:
 0. Помолиться
1. git clone https://github.com/miqqra/MyDudesGeo.git
2. docker-compose up -d

Инструкция по созданию дампа:
1. docker exec -t mydudesgeo-postgis_container-1 pg_dump -U postgres -F t postgres -f /tmp/mydatabase.tar
2. docker cp mydudesgeo-postgis_container-1:/tmp/mydatabase.tar ./initdb/mydatabase.tar

Для изменения токена телеграм бота, данных о БД
Перейдите в .env файл,

Пример .env файла:
TELEGRAM_BOT_TOKEN=changeme

POSTGRES_DB=postgres
POSTGRES_USER=postgres
POSTGRES_PASSWORD=postgres