
# 🏥 MediLabo - Application de Détection de Diabète de Type 2

## Contexte

Application destinée à **aider à la détection du diabète de type 2**.   
L’application repose sur une **architecture microservices Spring Boot**, orchestrée par un **Spring Cloud Gateway**.
Chaque service est livré sous forme d’image **Docker**.
  
---  

## Architecture de l’application

L’architecture est composée des microservices suivants :

- **Gateway** : service d’entrée basé sur Spring Cloud Gateway (routage vers les autres microservices).
- **Patient** : gestion des données patients (stockées en base relationnelle normalisée).
- **Assessment** : calcul des risques de diabète en fonction des données patients et des notes médicales.
- **Note** : stockage des notes médicales dans **MongoDB** (NoSql).
- **Front** : interface utilisateur pour accéder à l’application.
- **MongoDB** : base de données NoSQL pour le service `Note`.

Chaque service est isolé, packagé dans une image Docker, et déployé via **Docker Compose**.
  
---  

## 🚀 Prérequis

- [Docker](https://docs.docker.com/get-docker/)
- [Docker Compose](https://docs.docker.com/compose/)
- Java 17 (si exécution hors Docker)
- Maven (si compilation locale nécessaire)

---  

## Lancement du projet

1. **Cloner le dépôt** :
```bash  
git clone https://github.com/cnyo/medilabo.git```  
```

2. **Construire et lancer l’application avec Docker Compose**
```bash  
docker compose up --build -d
```  

## Configuration
1. Variables d’environnement principales (dans un .env par exemple)
```yaml  
# MongoDB Configuration  
MONGO_USERNAME=mongo_user  
MONGO_PASSWORD=secure_password  
MONGO_DATABASE=name_db  
  
 # Mongo Express ConfigurationMONGO_EXPRESS_USERNAME=admin  
MONGO_EXPRESS_PASSWORD=admin_password  
  
 # Spring ProfilesSPRING_PROFILES_ACTIVE=dev  
```  

2. Données d’exemple

Un fichier data.json est utilisé pour initialiser MongoDB avec des données de test lors du démarrage des conteneurs.  
Ce fichier est monté dans le conteneur grâce à :

```yaml  
# MongoDB Configuration  
volumes:  
 - ./src/resources:/docker-entrypoint-initdb.d  
```  

## Améliorations futures
## Green Code
* librairie CSS plus légère
* Utilisation de CDN
* Utilisation de DTO pour optimiser les réponses
* Centralisation des logs

2. autre amélioration possible
* Arbre de décision pour le calcul du risque (gestion plus pratique et sûr en cas de nouvelles règles à intégrer)
