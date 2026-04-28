package com.ndokoti.stock.service;

import com.ndokoti.stock.model.Produit;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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

    // ─────────────────────────────────────────────
    // 5. collect(groupingBy()) — Grouper par catégorie
    // ─────────────────────────────────────────────

    /**
     * Regroupe les produits par catégorie.
     * Résultat : {"Huiles" -> [p1, p2], "Céréales" -> [p3, p4, p5]}
     * Utilisé pour afficher le stock organisé par rayons.
     */
    public List <Produit> getProduitsParCategorie(List<Produit> produits, String categorie){
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


    /**
     * Retourne tous les produits dont le stock est en alerte.
     * Utilisé pour afficher les alertes sur le dashboard du gérant.
     */
    public  List<Produit>getProduitsenAlerte(List<Produit> produits){
        return produits
                .stream()
                // filter() garde seulement les éléments qui respectent la condition
                // estEnRuptureImminente() est la méthode qu'on a créée dans Produit
                .filter(Produit::estEnRuptureImminente)
                .collect(Collectors.toList());
    }


    /**
     * Retourne uniquement les noms de tous les produits.
     * map() transforme chaque Produit en son nom (String)
     */
    public List<String>getNomsDesProduits(List<Produit> produits){
        return produits
                .stream()
                .map(Produit::getNom)
                .collect(Collectors.toList());
    }


    public List<Double>getMargesDeProduits(List<Produit> produits){
        return produits
                .stream()
                .map(Produit::calculerMarge)
                .collect(Collectors.toList());
    }

    // ─────────────────────────────────────────────
    // 3. sorted() — Trier des produits
    // ─────────────────────────────────────────────

    /**
     * Retourne les produits triés par prix de vente croissant.
     * Affiché sur le tableau de bord : du moins cher au plus cher.
     */

   public  List<Produit>getProduitsTrisParPrix(List<Produit> produits) {
        return produits
                .stream()
                // Comparator.comparingDouble : compare les produits sur leur prixVente
                .sorted(Comparator.comparing(Produit::getPrixVente))
                .collect(Collectors.toList());
    }

    // ─────────────────────────────────────────────
    // 4. Chaînage — filter + map + sorted ensemble
    // ─────────────────────────────────────────────

    /**
     * Scénario réel : le gérant veut voir les noms des produits actifs
     * dont le stock est bas, triés par quantité croissante.
     */

    public List<String>getNomsProduitsActifsEnAlerte(List<Produit> produits) {
        return produits
                .stream()
                .filter(Produit::getActif)
                .filter(Produit::estEnRuptureImminente)
                .sorted(Comparator.comparing(Produit::getQuantiteStock))
                .map(Produit::getNom)
                .collect(Collectors.toList());
    }


    // ─────────────────────────────────────────────
    // 6. count() et anyMatch() — Compter et vérifier
    // ─────────────────────────────────────────────

    /**
     * Compte le nombre de produits en alerte.
     * Affiché dans le badge rouge sur le dashboard.
     */

    public long compterProduitsEnAlerte(List<Produit> produits) {
        return produits
                .stream()
                .filter(Produit::estEnRuptureImminente)
                .count();
    }

    /**
     * Vérifie si au moins un produit est en rupture totale (stock = 0).
     * Si true → alerte critique envoyée au gérant.
     */

    public  boolean existeRuptureTotale(List<Produit> produits) {
        return produits
                .stream()
                // anyMatch() : retourne true dès qu'un élément correspond
                .anyMatch(p->p.getQuantiteStock()!=null&&p.getQuantiteStock()==0);
    }


// ─────────────────────────────────────────────
    // 7. Optional — Chercher un produit sans risque
    // ─────────────────────────────────────────────

    /**
     * Cherche un produit par son nom.
     * Optional évite le NullPointerException si le produit n'existe pas.
     */
    public Optional<Produit> chercherParNom(List<Produit> produits, String nom) {
        return produits
                .stream()
                // findFirst() retourne Optional<Produit>
                // Optional = "peut contenir un Produit ou être vid
                .filter(p->p.getNom().equalsIgnoreCase(nom))
                .findFirst();
    }



}
