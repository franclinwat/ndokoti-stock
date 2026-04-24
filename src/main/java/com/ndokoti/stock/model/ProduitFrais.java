package com.ndokoti.stock.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

// @EqualsAndHashCode(callSuper = true) dit à Lombok d'inclure
// les attributs du PARENT (Produit) dans equals() et hashCode()
// Sans ça, deux ProduitFrais avec le même id seraient considérés différents

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class ProduitFrais extends Produit {

    // Attributs SPÉCIFIQUES aux produits frais
    private LocalDate dateExpiration;
    private boolean necessiteRefrigeration;

    /**
     * Un produit frais est périmé si sa date d'expiration
     * est avant aujourd'hui.
     * Exemple concret : yaourt, fromage, viande
     */
    public boolean estPerime() {
        if (dateExpiration == null) return false;
        return dateExpiration.isBefore(LocalDate.now());
    }

    /**
     * Nombre de jours restants avant expiration.
     * Si négatif = déjà périmé.
     */
    public long joursAvantExpiration() {
        if (dateExpiration == null) return Long.MAX_VALUE;
        return LocalDate.now().until(dateExpiration).getDays();
    }
}
