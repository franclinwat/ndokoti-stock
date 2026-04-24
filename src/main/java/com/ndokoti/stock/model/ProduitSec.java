package com.ndokoti.stock.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ProduitSec extends Produit {

    // Attributs SPÉCIFIQUES aux produits secs
    private Double poidsKg;

    // Comment le produit est conditionné
    private String conditionnement; // "sachet", "boite", "vrac", "carton"

    /**
     * Calcule le prix au kilo pour comparer les produits entre eux.
     * Utile pour afficher "2,50€/kg" sur l'étiquette.
     */
    public double calculerPrixAuKilo() {
        if (poidsKg == null || poidsKg == 0) return 0;
        return getPrixVente() / poidsKg;
    }
}
