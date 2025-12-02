FROM mongo:8

COPY init/data.json /docker-entrypoint-initdb.d/data.json
COPY init/init.sh /docker-entrypoint-initdb.d/init.sh
