/**
 * Package: com.jad.reportgeneration
 *
 * Contrat de package (frontières):
 * - API publique/stable exposée aux autres modules:
 *   - {@link com.jad.reportgeneration.IReportGenerator}
 *   - {@link com.jad.reportgeneration.ReportType}
 *
 * - Implémentations internes (détails non stables):
 *   - {@link com.jad.reportgeneration.AbstractReportGenerator}
 *   - {@link com.jad.reportgeneration.CSVReportGenerator}
 *   - {@link com.jad.reportgeneration.TextReportGenerator}
 *   - {@link com.jad.reportgeneration.ReportGenerator}
 *
 * Rôle: Génération de rapports (CSV, texte) à partir de données capteurs.
 *
 * Voir le diagramme de packages pour les dépendances et frontières:
 *   packages.puml (racine du projet)
 */
package com.jad.reportgeneration;
