# PLAN_REFACTOR (Avant exécution complète du refactoring)

## 1. Contexte & objectifs
Application multi-modules de collecte / agrégation de données capteurs et génération de rapports. Objectifs de l’activité :
1. Réduire les dépendances croisées et expliciter les frontières (modèle, SPI, implémentations, orchestration, UI).
2. Appliquer une séparation nette Contrats (API/SPI) vs Implémentations.
3. Centraliser la configuration Maven (Java 21, convergence des dépendances, plugins).
4. Garantir zéro changement fonctionnel (tests existants = oracle).

## 2. État initial (résumé)
Modules présents :
- `main-application` (orchestrateur démarrant tout mais très couplé aux impls)
- `utils` (logger / fonctions communes)
- `sensor-data-collection` (types + impl capteurs)
- `data-management` (interfaces + impl traitement / stockage)
- `report-generation` (interfaces + impl rapport)
- `user-interface` (dépend directement de modules métier)
- `test` (tests d’intégration & assertions de dépendances)

Problèmes identifiés :
- Les modèles (SensorData, SensorType, ISensor, etc.) sont mélangés avec les implémentations capteurs.
- Les contrats (IDataManager, IReportGenerator…) se trouvent dans les mêmes modules que leurs implémentations (empêche découplage et substitution).
- `user-interface` dépendait de `data-management` (impl) au lieu d’une abstraction.
- Couplage transitif inutile entre plusieurs modules via les types « sensor ».

## 3. Architecture cible (modules & dépendances directionnelles)
```
                 +------------------+
                 |   main-application (orchestrateur) |
                 +------------------+
                          | (compile)
        +-----------+-----------+-----------+
        |           |           |           |
   data-spi   report-spi   shared-model   user-interface
        |           |            ^             |
        |           |            |             |
  data-management  report-generation           |
        ^                 ^                    |
        |                 |                    |
        +--------- sensor-data-collection -----+

Légende: flèches = dépendances compile-time (impl → contrat / modèle). UI ne connaît que les contrats & modèles.
```

## 4. Règles de conception
- `shared-model` : contient uniquement les types de données transverses (record / enum / interfaces simples des capteurs) sans logique.
- `data-spi` / `report-spi` : exposent uniquement les interfaces métier (aucune impl, aucune dépendance vers impl modules).
- Implémentations (`data-management`, `report-generation`, `sensor-data-collection`) dépendent de `shared-model` + SPI correspondant, jamais de l’UI.
- `user-interface` dépend de `shared-model` + SPI (pas des impl). Elle consomme les abstractions injectées par `main-application`.
- `main-application` connaît tous les SPI + choisit les impl à câbler (composition root légère).
- `utils` reste transversal (logger) mais ne dépend d’aucun autre module applicatif.

## 5. Déplacements prévus
| Élément | Emplacement initial | Nouveau module | Remarques |
|---------|---------------------|----------------|-----------|
| `SensorData`, `SensorType`, `ISensor`, `ISensorFactory` | sensor-data-collection | shared-model | Extraction du modèle pur |
| `IDataManager`, `IDataProcessor`, `IDataStorage` | data-management | data-spi | SPI de gestion de données |
| `IReportGenerator`, `ReportType` | report-generation | report-spi | SPI de reporting |
| Placeholders (fichiers vides) | modules impl | restent | Évitent conflits FQCN pendant transition (supprimables en phase finale) |

## 6. Ajustements Maven planifiés
- Ajout modules : `shared-model`, `data-spi`, `report-spi` dans le POM parent.
- Centralisation : version JUnit, compiler plugin, surefire, enforcer (Java 21 + dependencyConvergence).
- Changement des portées : suppression d’abus de `provided`; mise à `compile` pour les SPI / modèles utilisés.
- Suppression dépendance directe `user-interface` → `data-management`; ajout dépendances vers `data-spi` + `shared-model`.
- `report-generation` dépend de `report-spi` + `shared-model` (ne dépend plus des capteurs directement sauf via modèle).

## 7. Étapes d’exécution
1. Créer modules SPI & modèle (pom + structure vide).
2. Déplacer types & interfaces (copier puis supprimer / placeholder pour préserver test green progressif).
3. Mettre à jour les POMs (ajouter dépendances minimales, retirer croisements, activer enforcer).
4. Compiler / corriger imports.
5. Exécuter tests — itérations jusqu’à verts.
6. Générer `dependency-tree.before.txt` (snapshot avant commit refactor final) puis `dependency-tree.after.txt`.
7. Rédiger documentation (plan + réflexion) + diagrammes packages.
8. Commit(s) structurés : before, refactor, after + docs.

## 8. Critères d’acceptation
- Tous les tests existants passent (zéro changement fonctionnel).
- Aucune dépendance UI → impl métier directe (uniquement via SPI / modèle).
- Les SPI ne dépendent que de `shared-model` (pas d’impl inverses).
- Enforcer passe (Java 21, convergence).
- Arbre des dépendances après : réduction des liens inter-modules (notamment suppression de dépendance UI → data-management et report-gen → sensor-data-collection pour les types internes).

## 9. Risques & mitigations (extraits pour réflexion future)
- Multiplication de modules = surcharge cognitive → Documentation + noms explicites.
- Placeholders oubliés → Phase de nettoyage finale recommandée.
- Tests ne couvrent pas potentiellement certains chemins d’injection → Vérifier manuellement le wiring principal.

---
Document rédigé avant finalisation complète du refactor (sert de trace de conception Proposé -> Cible).
