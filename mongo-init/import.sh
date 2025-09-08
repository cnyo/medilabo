#!/bin/bash
echo "Importing initial data into MongoDB..."
mongoimport \
  --host localhost \
  --db note \
  --collection note \
  --type json \
  --file /docker-entrypoint-initdb.d/data.json \
  --jsonArray \
  -u springuser \
  -p password \
  --authenticationDatabase admin