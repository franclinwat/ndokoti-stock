package com.ndokoti.stock.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Produit {

    private Long id;
    private String nom;
    private String description;
    private String categorie;
    private Double prixAchat;
    private Double prixVente;
    private String unite;
    private Integer quantiteStock;
    private Integer seuilAlerte;
    private Boolean actif = true;

    public double calculerMarge() {
        if (prixAchat == null || prixAchat == 0) return 0;
        return ((prixVente - prixAchat) / prixAchat) * 100;
    }

    public boolean estEnRuptureImminente() {
        if (quantiteStock == null || seuilAlerte == null) return false;
        return quantiteStock <= seuilAlerte;
    }



}
