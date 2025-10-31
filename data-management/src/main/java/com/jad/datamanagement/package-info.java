/**
 * Package: com.jad.datamanagement
 *
 * Contrat de package (frontières):
 * - API publique/stable exposée aux autres modules:
 *   - {@link com.jad.datamanagement.IDataManager}
 *   - {@link com.jad.datamanagement.IDataProcessor}
 *   - {@link com.jad.datamanagement.IDataStorage}
 *
 * - Implémentations internes (détails non stables):
 *   - {@link com.jad.datamanagement.DataManager}
 *   - {@link com.jad.datamanagement.DataProcessor}
 *   - {@link com.jad.datamanagement.DataStorage}
 *   - {@link com.jad.datamanagement.DataCollector}
 *   - {@link com.jad.datamanagement.DataValidator}
 *
 * Rôle: Calculs agrégés, collecte et stockage des données capteurs.
 *
 * Voir le diagramme de packages pour les dépendances et frontières:
 *   packages.puml (racine du projet)
 */
package com.jad.datamanagement;
