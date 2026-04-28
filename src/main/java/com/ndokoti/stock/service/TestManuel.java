package com.ndokoti.stock.service;

import com.ndokoti.stock.model.Produit;
import org.springframework.boot.env.YamlPropertySourceLoader;

import java.util.Arrays;
import java.util.List;

public class TestManuel {
    public static void main(String[] args) {

        // Données de test — 5 produits Ndokoti Shop
        Produit riz = new Produit(1L, "Riz parfumé", "Riz thaï",
                "Céréales", 800.0, 1200.0, "kg", 3, 5, true);

        Produit huile = new Produit(2L, "Huile de palme", "1 litre",
                "Huiles", 600.0, 900.0, "litre", 12, 5, true);

        Produit sel = new Produit(3L, "Sel fin", "Sel iodé",
                "Condiments", 100.0, 200.0, "sachet", 2, 5, true);

        Produit sucre = new Produit(4L, "Sucre cristal", "1kg",
                "Céréales", 400.0, 650.0, "kg", 0, 5, true);

        Produit poivre = new Produit(5L, "Poivre noir", "Epice",
                "Condiments", 300.0, 700.0, "sachet", 8, 5, true);

        List<Produit> stock = Arrays.asList(riz, huile, sel, sucre, poivre);

        StockAnalyseService service = new StockAnalyseService();


        // TEST 1 : produits en alerte (stock <= seuilAlerte)
        System.out.println("=== PRODUITS EN ALERTE ===");
        service.getProduitsenAlerte(stock)
                .forEach(p -> System.out.println(p.getNom() + " — stock: " + p.getQuantiteStock()));

        // TEST 2 : noms triés par stock
        System.out.println("\n=== NOMS EN ALERTE (triés) ===");
        service.getNomsProduitsActifsEnAlerte(stock)
                .forEach(System.out::println);

        // TEST 3 : grouper par catégorie
        System.out.println("\n=== PAR CATÉGORIE ===");
        service.grouperParCategorie(stock)
                .forEach((cat, prods) -> System.out.println(cat + " : " + prods.size() + " produits"));

        // TEST 4 : rupture totale ?
        System.out.println("\n=== RUPTURE TOTALE ? ===");
        System.out.println(service.existeRuptureTotale(stock));
    }

}