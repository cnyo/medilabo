FROM mongo:8

# Copier les scripts d'initialisation si nécessaire
COPY init/ /docker-entrypoint-initdb.d/

EXPOSE 27017