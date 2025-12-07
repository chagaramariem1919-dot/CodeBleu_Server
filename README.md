#  CodeBleu_Server - Serveur Central

##  Description
Serveur central du système de gestion d'urgences médicales Code Bleu. Il gère les communications entre les infirmiers et les médecins via deux technologies : **CORBA** pour les déclarations d'urgence et **RMI** pour les notifications en temps réel.

##  Architecture Technique
Le serveur utilise une architecture hybride :
- **CORBA (Common Object Request Broker Architecture)** : Pour les communications structurées client-serveur avec les infirmiers
- **RMI (Remote Method Invocation)** : Pour les notifications asynchrones push vers les médecins
- **Gestionnaire central** : Pour la coordination et la persistance des données d'urgence

##  Fonctionnalités Principales
1. **Réception d'urgences** : Interface CORBA pour recevoir les déclarations des infirmiers
2. **Stockage centralisé** : Gestion et persistance des urgences déclarées
3. **Notification en temps réel** : Système RMI de callback pour alerter instantanément les médecins
4. **Gestion des connexions** : Maintenance des sessions des clients médecins connectés

##  Installation et Exécution

### Prérequis
- JDK 1.8 ou supérieur
- ORB CORBA (inclus dans JDK)
- RMI Registry

### Compilation
```bash
# 1. Générer les stubs CORBA depuis Urgence.idl
idlj -fall Urgence.idl

# 2. Compiler le projet
javac -cp . src/corba/*.java main/*.java rmi/*.java service/*.java

# 3. Démarrer le serveur
java -cp . main.ServerMain
