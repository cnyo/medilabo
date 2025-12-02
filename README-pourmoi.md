# Docker

### Images
Build sans tagger l'image
```bash
docker build -t medilabo-front .
```

Build et tag l'image
```bash
docker build -t medilabo-front .
```

Rennommer l'image
```bash
docker tag medilabo-front medilabo-front:1.0.0
```

Envoie l'image vers Docker Hub
```bash
docker push medilabo-front:1.0.0
```

Liste toutes les images
```bash
docker images
```

Supprimer une image
```bash
docker rmi medilabo-front
```

### Les volumes
Lister les volumes
```bash
docker volume ls
```

Supprimer un volume
```bash
docker volume rm VOLUME_NAME_OR_ID VOLUME_NAME_OR_ID
```

Lister les volumes non référencés par un conteneur
```bash
docker volume ls -f dangling=true
```

Supprimer les volumes non référencés par un conteneur
```bash
docker volume prune
```

Supprimer un volume et son conteneur
```bash
docker rm -v container_name
```

Supprimer tous les conteneurs et les volumes
```bash
docker compose down -v
```

### Conteneurs
Liste tous les conteneurs
```bash
docker ps
```

```bash
docker run -p 80:8080 medilabo-front
```

# Docker compose
### Lister les containers
```bash
docker compose ps
```

```bash
docker compose up -d
```

```bash
docker compose down
```

# Logs
### Voir tous les logs
```bash
docker compose logs medilabo-front-1
```

### Suivre les logs en direct
```bash
docker logs medilabo-front-1 -f
docker compose logs --follow (version longue)
```

### Voir les logs d’un seul service
```bash
docker compose logs -f app
docker compose logs -f db
```

### Voir les logs récents
```bash
docker compose logs --tail=100 -f app
```

# Docker exec
### Entrer dans le container
```bash
docker exec -it medilabo-front-1 bash
```