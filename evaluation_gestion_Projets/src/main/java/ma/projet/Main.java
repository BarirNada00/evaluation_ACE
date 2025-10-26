package ma.projet;

import ma.projet.classes.*;
import ma.projet.service.*;
import ma.projet.util.HibernateConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(HibernateConfig.class);

        ProjetService projetService = context.getBean(ProjetService.class);
        TacheService tacheService = context.getBean(TacheService.class);
        EmployeService employeService = context.getBean(EmployeService.class);
        EmployeTacheService employeTacheService = context.getBean(EmployeTacheService.class);

        try {
            nettoyerDonnees(employeTacheService, tacheService, projetService, employeService);

            Employe emp1 = new Employe("Khalid", "Yassine", "yassine.khalid@email.com", "0612345678");
            Employe emp2 = new Employe("Sofia", "El Amrani", "sofia.elamrani@email.com", "0678901234");

            employeService.create(emp1);
            employeService.create(emp2);
            System.out.println("Employés créés");

            Projet p1 = new Projet("Application Mobile Banque",
                    LocalDate.of(2022, 3, 1),
                    LocalDate.of(2022, 12, 15),
                    120000.0, emp1);

            Projet p2 = new Projet("Système de Gestion RH",
                    LocalDate.of(2022, 5, 10),
                    LocalDate.of(2022, 11, 30),
                    95000.0, emp2);

            projetService.create(p1);
            projetService.create(p2);
            System.out.println("Projets créés");

            Tache t1 = new Tache("Étude de faisabilité", "Analyse préliminaire et faisabilité", 3000.0,
                    LocalDate.of(2022, 3, 5), LocalDate.of(2022, 4, 5), p1);
            t1.setDateDebutReelle(LocalDate.of(2022, 3, 10));
            t1.setDateFinReelle(LocalDate.of(2022, 4, 1));

            Tache t2 = new Tache("Prototype UI", "Développement du prototype interface", 4000.0,
                    LocalDate.of(2022, 4, 6), LocalDate.of(2022, 5, 10), p1);
            t2.setDateDebutReelle(LocalDate.of(2022, 4, 10));
            t2.setDateFinReelle(LocalDate.of(2022, 5, 5));

            Tache t3 = new Tache("Tests fonctionnels", "Tests et validation", 3500.0,
                    LocalDate.of(2022, 5, 11), LocalDate.of(2022, 6, 15), p1);
            t3.setDateDebutReelle(LocalDate.of(2022, 5, 12));
            t3.setDateFinReelle(LocalDate.of(2022, 6, 10));

            Tache t4 = new Tache("Recrutement", "Mise en place processus recrutement", 2500.0,
                    LocalDate.of(2022, 5, 15), LocalDate.of(2022, 7, 1), p2);

            tacheService.create(t1);
            tacheService.create(t2);
            tacheService.create(t3);
            tacheService.create(t4);
            System.out.println("Tâches créées");

            EmployeTache et1 = new EmployeTache(
                    LocalDate.of(2022, 3, 10), LocalDate.of(2022, 4, 1), emp1, t1);
            EmployeTache et2 = new EmployeTache(
                    LocalDate.of(2022, 4, 10), LocalDate.of(2022, 5, 5), emp1, t2);
            EmployeTache et3 = new EmployeTache(
                    LocalDate.of(2022, 5, 12), LocalDate.of(2022, 6, 10), emp2, t3);

            employeTacheService.create(et1);
            employeTacheService.create(et2);
            employeTacheService.create(et3);
            System.out.println("Affectations employé-tâche créées");

            System.out.println("\n=== TESTS DES FONCTIONNALITÉS ===");

            System.out.println("\n--- Détails du projet 'Application Mobile Banque' ---");
            projetService.afficherDetailsProjet(p1.getId());

            System.out.println("\n--- Tâches réalisées par Khalid Yassine ---");
            employeService.afficherTachesRealiseesParEmploye(emp1.getId());

            System.out.println("\n--- Projets gérés par Sofia El Amrani ---");
            List<Projet> projetsSofia = employeService.getProjetsGeresByEmploye(emp2.getId());
            for (Projet p : projetsSofia) {
                System.out.printf("- %s (Budget: %.2f DH)%n", p.getNom(), p.getBudget());
            }

            System.out.println("\n--- Tâches avec prix > 3000 DH ---");
            List<Tache> tachesCheres = tacheService.getTachesPrixSuperieur1000();
            for (Tache t : tachesCheres) {
                System.out.printf("- %s: %.2f DH (Projet: %s)%n",
                        t.getNom(), t.getPrix(), t.getProjet().getNom());
            }

            System.out.println("\n--- Tâches réalisées entre 2022-04-01 et 2022-06-30 ---");
            List<Tache> tachesDates = tacheService.getTachesRealiseesBetweenDates(
                    LocalDate.of(2022, 4, 1), LocalDate.of(2022, 6, 30));
            for (Tache t : tachesDates) {
                System.out.printf("- %s: %s à %s%n",
                        t.getNom(), t.getDateDebutReelle(), t.getDateFinReelle());
            }

            System.out.println("\n Application de gestion de projets exécutée avec succès !");

        } catch (Exception e) {
            System.err.println(" Erreur: " + e.getMessage());
            e.printStackTrace();
        } finally {
            context.close();
        }
    }

    private static void nettoyerDonnees(EmployeTacheService ets, TacheService ts,
                                        ProjetService ps, EmployeService es) {
        List<EmployeTache> employeTaches = ets.findAll();
        for (EmployeTache et : employeTaches) {
            ets.delete(et);
        }

        List<Tache> taches = ts.findAll();
        for (Tache t : taches) {
            ts.delete(t);
        }

        List<Projet> projets = ps.findAll();
        for (Projet p : projets) {
            ps.delete(p);
        }

        List<Employe> employes = es.findAll();
        for (Employe e : employes) {
            es.delete(e);
        }
    }
}
