package ma.projet;

import ma.projet.classes.Categorie;
import ma.projet.classes.Commande;
import ma.projet.classes.LigneCommandeProduit;
import ma.projet.classes.Produit;
import ma.projet.service.CategorieService;
import ma.projet.service.CommandeService;
import ma.projet.service.LigneCommandeService;
import ma.projet.service.ProduitService;
import ma.projet.util.HibernateConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(HibernateConfig.class);

        ProduitService produitService = context.getBean(ProduitService.class);
        CommandeService commandeService = context.getBean(CommandeService.class);
        LigneCommandeService ligneService = context.getBean(LigneCommandeService.class);
        CategorieService categorieService = context.getBean(CategorieService.class);

        Categorie catg1 = new Categorie();
        catg1.setCode("C01");
        catg1.setLibelle("Catégorie 1");

        Categorie catg2 = new Categorie();
        catg2.setCode("C02");
        catg2.setLibelle("Catégorie 2");

        categorieService.create(catg1);
        categorieService.create(catg2);

        Produit p1 = new Produit();
        p1.setReference("AA12");
        p1.setPrix(135f);
        p1.setCategorie(catg1);

        Produit p2 = new Produit();
        p2.setReference("ZP85");
        p2.setPrix(200f);
        p2.setCategorie(catg1);

        Produit p3 = new Produit();
        p3.setReference("WW90");
        p3.setPrix(400f);
        p3.setCategorie(catg2);

        produitService.create(p1);
        produitService.create(p2);
        produitService.create(p3);

        Commande c1 = new Commande();
        c1.setDate(LocalDate.of(2016, 2, 18));
        commandeService.create(c1);

        Commande c2 = new Commande();
        c2.setDate(LocalDate.of(2016, 5, 21));
        commandeService.create(c2);

        LigneCommandeProduit l1 = new LigneCommandeProduit();
        l1.setProduit(p1);
        l1.setCommande(c1);
        l1.setQuantite(8);

        LigneCommandeProduit l2 = new LigneCommandeProduit();
        l2.setProduit(p2);
        l2.setCommande(c1);
        l2.setQuantite(18);

        LigneCommandeProduit l3 = new LigneCommandeProduit();
        l3.setProduit(p3);
        l3.setCommande(c2);
        l3.setQuantite(3);

        ligneService.create(l1);
        ligneService.create(l2);
        ligneService.create(l3);

        System.out.println("\n***Produits par catégorie C1***");
        List<Produit> produitsCat1 = produitService.getProduitsByCategorie(catg1);
        for (Produit p : produitsCat1) {
            System.out.printf("Réf : %-5s Prix : %-6.2f Catégorie : %s%n",
                    p.getReference(), p.getPrix(), p.getCategorie().getLibelle());
        }

        System.out.println("\n***Produits par commande c1***");
        List<Produit> produitsCommande1 = produitService.getProduitsByCommande(c1.getId());
        for (Produit p : produitsCommande1) {
            System.out.printf("Réf : %-5s Prix : %-6.2f%n", p.getReference(), p.getPrix());
        }

        System.out.println("\n***Produits commandés entre 2016-02-18 et 2016-05-21***");
        LocalDate start = LocalDate.of(2016, 1, 1);
        LocalDate end = LocalDate.of(2016, 12, 31);
        List<Produit> produitsDates = produitService.getProduitsCommandesBetweenDates(start, end);
        for (Produit p : produitsDates) {
            System.out.printf("Réf : %-5s Prix : %-6.2f%n", p.getReference(), p.getPrix());
        }

        System.out.println("\n***Produits prix > 100 DH***");
        List<Produit> produitsChers = produitService.getProduitsPrixSuperieur(100f);
        for (Produit p : produitsChers) {
            System.out.printf("Réf : %-5s Prix : %-6.2f%n", p.getReference(), p.getPrix());
        }

        System.out.println("\n***Détails de la commande 1***");
        produitService.afficherDetailsCommande(c1.getId());

        context.close();
    }
}