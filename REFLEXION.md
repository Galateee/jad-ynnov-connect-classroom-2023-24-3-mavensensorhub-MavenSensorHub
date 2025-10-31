# Réflexion sur l'Architecture MavenSensorHub

## Choix de design de packages

L'architecture applique le **Dependency Inversion Principle (DIP)** via un module `sensor-api` contenant uniquement les contrats (`ISensor`, `ISensorFactory`, `SensorType`, `SensorData`, `DataQueries`). Les modules métier (`data-management`, `report-generation`, `user-interface`) dépendent de ces abstractions plutôt que des implémentations concrètes de `sensor-data-collection`. Cela élimine les couplages forts et respecte les contraintes architecturales imposées par les tests.

Les contrats publics sont documentés via `package-info.java` dans `data-management` et `report-generation` pour clarifier ce qui est exposé (interfaces) versus ce qui est interne (implémentations).

## Ce qui a changé dans le parent POM et pourquoi

Le parent POM centralise désormais toute la configuration critique :
- **`dependencyManagement`** : verrouillage des versions de JUnit (5.10.0) et de tous les modules internes (`sensor-api`, `utils`, etc.) pour éviter les conflits et garantir la cohérence.
- **`maven-compiler-plugin`** : configuration `<release>21</release>` pour compiler avec Java 21 de manière portable.
- **`maven-enforcer-plugin`** : règles strictes exigeant Java 21+ et la convergence des dépendances (`dependencyConvergence`), empêchant les versions conflictuelles.
- **`maven-dependency-plugin`** : génération automatique de `dependency-tree.txt` avant les tests pour satisfaire les assertions d'architecture.

Cela permet aux modules enfants d'hériter de la configuration sans répétition (DRY) et garantit l'homogénéité du build.

## Risques évités

- **Dérive de versions** : sans `dependencyManagement`, chaque module pourrait déclarer des versions différentes de JUnit ou de modules internes, créant des conflits subtils au runtime.
- **Dépendances cycliques** : l'ancienne architecture (`user-interface` → `data-management` → `sensor-data-collection` ← `user-interface`) créait un couplage fort. Le découplage via `sensor-api` élimine ce risque.
- **Régression Java** : l'enforcer empêche un développeur de builder avec Java 17 par erreur, garantissant la compatibilité Java 21.

## Prochaine amélioration

Extraire une **interface `IReportDataProvider`** dans `sensor-api` pour découpler complètement `report-generation` de `data-management` (actuellement, `main-application` injecte `IDataManager.getAllData()` directement). Cela permettrait de tester les générateurs de rapports de manière isolée avec des mocks et d'appliquer le **Interface Segregation Principle** : les rapports n'ont besoin que d'un fournisseur de données (`List<SensorData>`), pas de toute l'API de gestion.
