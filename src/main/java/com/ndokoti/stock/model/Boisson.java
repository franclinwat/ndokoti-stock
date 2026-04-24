package com.ndokoti.stock.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Boisson extends Produit{

    private Double volumeLitre;
    private boolean estAlcoolisee;
    private Double tauxAlcool; // null si non alcoolisée

    /**
     * Dans une épicerie, les boissons alcoolisées ont
     * des règles de vente différentes (âge minimum, etc.)
     */
    public boolean necessiteVerificationAge() {
        return estAlcoolisee && tauxAlcool != null && tauxAlcool > 0;
    }
}
