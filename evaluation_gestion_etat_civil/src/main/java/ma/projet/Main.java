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

        HommeService hommeService = context.getBean(HommeService.class);
        FemmeService femmeService = context.getBean(FemmeService.class);
        MariageService mariageService = context.getBean(MariageService.class);

        try {

            nettoyerDonnees(mariageService, hommeService, femmeService);

            Homme h1 = new Homme("EL AMRANI", "YASSIN", LocalDate.of(1958, 4, 20));
            Homme h2 = new Homme("TOUIL", "SAID", LocalDate.of(1963, 9, 15));
            Homme h3 = new Homme("RAFIQI", "MUSTAPHA", LocalDate.of(1972, 12, 1));
            Homme h4 = new Homme("ZAHIDI", "HICHEM", LocalDate.of(1978, 6, 10));
            Homme h5 = new Homme("BOUTALIB", "FAOUZI", LocalDate.of(1983, 11, 22));

            hommeService.create(h1);
            hommeService.create(h2);
            hommeService.create(h3);
            hommeService.create(h4);
            hommeService.create(h5);
            System.out.println("5 hommes créés");

            Femme f1 = new Femme("NOURA", "AZZOUZ", LocalDate.of(1962, 1, 18));
            Femme f2 = new Femme("IMANE", "CHERKAOUI", LocalDate.of(1971, 5, 9));
            Femme f3 = new Femme("LAILA", "DAHBI", LocalDate.of(1969, 8, 30));
            Femme f4 = new Femme("HIND", "EL MANSOURI", LocalDate.of(1974, 3, 4));
            Femme f5 = new Femme("SAIDA", "FASSI", LocalDate.of(1977, 10, 27));
            Femme f6 = new Femme("MERYEM", "GHANDOUR", LocalDate.of(1979, 12, 12));
            Femme f7 = new Femme("SALMA", "HARIRI", LocalDate.of(1981, 7, 6));
            Femme f8 = new Femme("RANIA", "JABIRI", LocalDate.of(1983, 2, 19));
            Femme f9 = new Femme("SABRINA", "KAMAL", LocalDate.of(1986, 9, 14));
            Femme f10 = new Femme("YASMINA", "LAMRANI", LocalDate.of(1989, 11, 3));

            femmeService.create(f1);
            femmeService.create(f2);
            femmeService.create(f3);
            femmeService.create(f4);
            femmeService.create(f5);
            femmeService.create(f6);
            femmeService.create(f7);
            femmeService.create(f8);
            femmeService.create(f9);
            femmeService.create(f10);
            System.out.println("10 femmes créées");

            Mariage m1 = new Mariage(LocalDate.of(1988, 7, 14), h1, f1);
            m1.setNombreEnfants(3);

            Mariage m2 = new Mariage(LocalDate.of(1993, 8, 22), h1, f2);
            m2.setNombreEnfants(1);

            Mariage m3 = new Mariage(LocalDate.of(1998, 4, 17), h1, f3);
            m3.setNombreEnfants(2);

            Mariage m4 = new Mariage(LocalDate.of(1987, 5, 10), LocalDate.of(1989, 5, 9), 0, h1, f4);

            Mariage m5 = new Mariage(LocalDate.of(1991, 3, 5), h2, f5);
            m5.setNombreEnfants(4);

            Mariage m6 = new Mariage(LocalDate.of(2003, 11, 21), h2, f6);
            m6.setNombreEnfants(2);

            Mariage m7 = new Mariage(LocalDate.of(1996, 6, 25), h3, f7);
            m7.setNombreEnfants(3);

            Mariage m8 = new Mariage(LocalDate.of(2001, 1, 30), h3, f8);
            m8.setNombreEnfants(1);

            Mariage m9 = new Mariage(LocalDate.of(2006, 10, 15), h3, f9);
            m9.setNombreEnfants(2);

            Mariage m10 = new Mariage(LocalDate.of(2011, 9, 9), h3, f10);
            m10.setNombreEnfants(0);

            Mariage m11 = new Mariage(LocalDate.of(2007, 12, 4), h4, f1);
            m11.setNombreEnfants(1);

            Mariage m12 = new Mariage(LocalDate.of(2013, 3, 29), h5, f2);
            m12.setNombreEnfants(3);

            mariageService.create(m1);
            mariageService.create(m2);
            mariageService.create(m3);
            mariageService.create(m4);
            mariageService.create(m5);
            mariageService.create(m6);
            mariageService.create(m7);
            mariageService.create(m8);
            mariageService.create(m9);
            mariageService.create(m10);
            mariageService.create(m11);
            mariageService.create(m12);
            System.out.println("Mariages créés");

            System.out.println("\n=== TESTS DES FONCTIONNALITÉS ===");

            System.out.println("\n--- Liste des femmes ---");
            List<Femme> femmes = femmeService.findAll();
            for (Femme femme : femmes) {
                System.out.printf("- %s (Née le: %s)%n", femme, femme.getDateNaissance());
            }

            System.out.println("\n--- Femme la plus âgée ---");
            Femme femmePlusAgee = femmeService.findFemmeLaPlusAgee();
            if (femmePlusAgee != null) {
                System.out.printf("%s (Née le: %s)%n", femmePlusAgee, femmePlusAgee.getDateNaissance());
            }

            System.out.println("\n--- Épouses de EL AMRANI YASSIN entre 1985 et 2000 ---");
            List<Femme> epouses = hommeService.getEpousesByHommeBetweenDates(
                    h1.getId(), LocalDate.of(1985, 1, 1), LocalDate.of(2000, 12, 31));
            for (Femme epouse : epouses) {
                System.out.printf("- %s%n", epouse);
            }

            System.out.println("\n--- Nombre d'enfants de NOURA AZZOUZ entre 1985-2000 ---");
            long nbEnfants = femmeService.getNombreEnfantsBetweenDates(
                    f1.getId(), LocalDate.of(1985, 1, 1), LocalDate.of(2000, 12, 31));
            System.out.printf("Nombre d'enfants: %d%n", nbEnfants);

            System.out.println("\n--- Femmes mariées au moins deux fois ---");
            femmeService.afficherFemmesMarieesPlusieursFois();

            System.out.println("\n--- Hommes mariés à au moins 4 femmes entre 1985-2015 ---");
            long nbHommes = hommeService.countHommesMariesQuatreFemmesBetweenDates(
                    LocalDate.of(1985, 1, 1), LocalDate.of(2015, 12, 31));
            System.out.printf("Nombre d'hommes mariés à au moins 4 femmes: %d%n", nbHommes);

            System.out.println("\n--- Détails des mariages de EL AMRANI YASSIN ---");
            hommeService.afficherMariagesHomme(h1.getId());

            System.out.println("\n Application de gestion de l'état civil exécutée avec succès !");

        } catch (Exception e) {
            System.err.println(" Erreur: " + e.getMessage());
            e.printStackTrace();
        } finally {
            context.close();
        }
    }

    private static void nettoyerDonnees(MariageService ms, HommeService hs, FemmeService fs) {
        List<Mariage> mariages = ms.findAll();
        for (Mariage m : mariages) {
            ms.delete(m);
        }

        List<Homme> hommes = hs.findAll();
        for (Homme h : hommes) {
            hs.delete(h);
        }

        List<Femme> femmes = fs.findAll();
        for (Femme f : femmes) {
            fs.delete(f);
        }
    }
}
