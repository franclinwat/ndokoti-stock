package com.ndokoti.stock.service;

import com.ndokoti.stock.model.Produit;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Service d'analyse du stock — utilise Stream API + Lambda.
 * Principe S-SOLID : ce service a UNE responsabilité — analyser le stock.
 */
@Service
public class StockAnalyseService {
    /**
     * Retourne tous les produits dont le stock est sous le seuil d'alerte.
     * Utilisé pour générer les alertes dans l'interface Ndokoti Shop.
     */

    public List<Produit>getProduitsSousSeuil(List<Produit> produits){
        return produits.
                stream()
                .filter(Produit::estEnRuptureImminente)
                .sorted(Comparator.comparing(Produit::getNom))
                .collect(Collectors.toList());
    }

    /**
            * Calcule la valeur totale du stock (prix achat × quantité).
            * Utile pour les rapports financiers du magasin.
     */

    public double calculerValeurTotaleStock(List<Produit> produits){
        return produits
                .stream()
                .filter(p->Boolean.TRUE.equals(p.getActif()))
                .mapToDouble(p->p.getPrixAchat()*p.getQuantiteStock())
                .sum();

    }

    /**
     * Regroupe les produits par catégorie.
     * Utile pour l'affichage par rayon dans l'interface.
     */
    public Map<String, List<Produit>> grouperParCategorie(List<Produit> produits) {
        return produits.stream()
                .collect(Collectors.groupingBy(Produit::getCategorie));
    }

    /**
     * Retourne les N produits les plus chers.
     * Utile pour le tableau de bord du gérant.
     */
    public List<Produit> getTopProduitsParPrix(List<Produit> produits, int n) {
        return produits.stream()
                .sorted(Comparator.comparing(Produit::getPrixVente).reversed())
                .limit(n)
                .collect(Collectors.toList());
    }

    /**
     * Vérifie si un produit existe dans le stock par son nom.
     */
    public boolean produitExiste(List<Produit> produits, String nom) {
        return produits.stream()
                .anyMatch(p -> p.getNom().equalsIgnoreCase(nom));
    }

    List <Produit> getProduitsParCategorie(List<Produit> produits, String categorie){
        return produits
                .stream()
                .filter(p->p.getCategorie().equalsIgnoreCase(categorie))
                .collect(Collectors.toList());
    }


    //MARGE MOYENNE :(marge brute/cout d achat)*100

    double calculerMargeMoyenne(List<Produit> produits){
        return produits
                .stream()
                .mapToDouble(p->(p.calculerMarge()/p.getPrixAchat())*100)
                .sum();
    }

}
