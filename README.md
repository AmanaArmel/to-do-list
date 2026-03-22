# To-Do List API

Ce projet est une API REST de gestion de listes de tâches sécurisée, développée avec **Spring Boot**. Elle permet aux utilisateurs de s'inscrire, de s'authentifier via **JWT** et de gérer leurs propres tâches de manière totalement isolée.

## Fonctionnalités
* **Authentification JWT** : Sécurisation des endpoints avec des jetons stateless.
* **Gestion des Tâches (CRUD)** : Création, lecture, mise à jour et suppression de tâches.
* **Isolation des données** : Un utilisateur ne peut accéder qu'aux tâches qu'il a créées.
* **Documentation OpenAPI** : Interface Swagger UI intégrée pour tester l'API facilement.
* **Base de données robuste** : Utilisation de PostgreSQL avec Liquibase pour le versionnement du schéma.

## Stack Technique
* **Backend** : Java 17, Spring Boot 4.0.3
* **Sécurité** : Spring Security, JWT
* **Base de données** : PostgreSQL
* **Migration BD** : Liquibase
* **Outils** : Docker, Maven, Lombok, MapStruct


## Installation et Lancement

### Prérequis
* Docker et Docker Compose installés sur votre machine.

### Démarrage rapide avec Docker
1. **Cloner le projet** :
   ```bash
   git clone <url-du-repo>
   cd to-do-list
2. **Lancer l'application** :
    ```bash
   docker compose up --build
L'API sera accessible sur http://localhost:8081
## Tester l'API
### Accéder à Swagger :
Rendez-vous sur : http://localhost:8081/api/v1/swagger-ui/index.html 
### S'authentifier :
Utilisez l'endpoint `POST /api/v1/auth/register` pour créer un compte.

Utilisez l'endpoint `POST /api/v1/auth/login` pour récupérer votre `access_token.`

Cliquez sur le bouton `"Authorize"` en haut à droite de Swagger et collez uniquement le token.
### Gérer les tâches :
* Créer une tâche : `POST /api/v1/tasks`

* Lister vos tâches : `GET /api/v1/tasks**`

* Mettre à jour : `PUT /api/v1/tasks/{id}`

* Terminer une tâche : `PATCH /api/v1/tasks/{id}/complete`

## Gestion de la Base de Données avec pgAdmin

Ce projet inclut un conteneur **pgAdmin4** configuré via Docker Compose pour vous permettre d'explorer et d'administrer facilement la base de données PostgreSQL via une interface web.

### 1. Accéder à l'interface
Une fois les conteneurs lancés avec `docker compose up`, ouvrez votre navigateur et allez sur :
* **URL** : [http://localhost:5050](http://localhost:5050) *(modifiez le port si différent dans votre docker-compose)*

### 2. Se connecter à pgAdmin
Utilisez les identifiants définis dans les variables d'environnement de pgAdmin :
* **Email** : `admin@admin.com`
* **Mot de passe** : `admin`

### 3. Connecter le serveur PostgreSQL
Lors de votre première connexion, vous devrez lier pgAdmin à votre conteneur PostgreSQL :
1. Dans le menu de gauche, faites un clic droit sur **Servers** > **Register** > **Server...**
2. **Onglet "General"** : Donnez un nom à votre serveur (ex: `ToDoList-DB`).
3. **Onglet "Connection"** :
   * **Host name/address** : `postgres` 
   * **Port** : `5432`
   * **Maintenance database** : `taskdb`
   * **Username** : `postgres`
   * **Password** : `password`
4. Cliquez sur **Save**.

Vous pouvez maintenant visualiser vos tables `users`, `tasks`, ainsi que les tables de suivi générées par Liquibase (`databasechangelog`).
