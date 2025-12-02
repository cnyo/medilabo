#!/bin/bash

echo ">>> Import initial JSON data into MongoDB..."

mongoimport \
  --username "$MONGO_INITDB_ROOT_USERNAME" \
  --password "$MONGO_INITDB_ROOT_PASSWORD" \
  --authenticationDatabase "admin" \
  --db note_db \
  --collection note \
  --jsonArray \
  /docker-entrypoint-initdb.d/data.json
