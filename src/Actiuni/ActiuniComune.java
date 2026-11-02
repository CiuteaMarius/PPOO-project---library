package Actiuni;

import Exceptii.CatInexistenta;
import Interfata.OptiuniComune;
import ObiecteReale.Carte;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Înglobează funcționalitățile de căutare și afișare a datelor din catalog.
 * Aceste metode sunt universale și pot fi apelate de orice actor din sistem, independent de nivelul de permisiuni.
 */
public class ActiuniComune {

    /**
     * Parcurge întregul dicționar de cărți și afișează structurat fiecare carte. Lucrările sunt grupate sub numele categoriei din care fac parte.
     * @param memorie instanța care furnizează acces la dicționarul bibliotecii
     */
    public void afiseazaTOT(ManagerMemorie memorie)
    {
        for(String key : memorie.getBiblioteca().keySet())
        {
            System.out.println("Categoria: " + key.toUpperCase());

            for(Carte c : memorie.getBiblioteca().get(key))
            {
                System.out.println("    "
                        + c.toString());
            }

        }
    }


    /**
     * Solicită introducerea unui autor de la tastatură și afișează toate volumele publicate de acela.
     * @param memorie instanța care furnizează acces la dicționarul bibliotecii
     */
    public void dupa_autor(ManagerMemorie memorie)
    {
        System.out.println("Introduce numele unui autor: ");

        Scanner scan = new Scanner(System.in);
        String autor = scan.nextLine();

        System.out.println("Cărțile autorului" + " sunt: ");
        boolean isFound = false;
        for(String key : memorie.getBiblioteca().keySet())
        {
            for(Carte c : memorie.getBiblioteca().get(key))
            {
                if(c.getAutor().equalsIgnoreCase(autor))
                {
                    isFound = true;
                    System.out.println(c.getTitlu());
                }

            }

        }
        if(isFound == false)
        {
            System.out.println("Nu am găsit cărți de la acest autor. Încercați să introduceți numele unui alt autor sau verificați dacă ați scris corect numele autorului.");
        }
    }


    /**
     * Returnează pe ecran lista lucrărilor asociate unui gen literar specific.
     * @param memorie instanța care furnizează acces la dicționarul bibliotecii
     * @param dorinta textul reprezentând numele genului literar căutat
     * @throws CatInexistenta dacă textul furnizat prin parametrul {@code dorinta} nu figurează ca o cheie validă în dicționar
     */
    public void cautaCategorie(ManagerMemorie memorie, String dorinta) throws CatInexistenta {
        if (memorie.getBiblioteca().containsKey(dorinta)) {
            System.out.println("Pentru categoria " + dorinta + " avem următoarele lucrări:");
            for (Carte c : memorie.getBiblioteca().get(dorinta)) {
                System.out.println(c.toString());
            }
        } else {

            throw new CatInexistenta("Categoria '" + dorinta + "' nu există în bibliotecă!");
        }
    }


    /**
     * Gestionează interacțiunea cu utilizatorul pentru filtrarea pe categorii.
     * Oferă opțiunea de a tasta „all” pentru a lista genurile disponibile și prinde excepția declanșată de metoda {@link #cautaCategorie(ManagerMemorie, String)} pentru a permite o nouă încercare.
     * @param memorie instanța care furnizează acces la dicționarul bibliotecii
     */
    public void dupa_categorie(ManagerMemorie memorie)
    {
        System.out.println("Scrieți categoria care vă interesează sau introduceți \"all\" pentru a vă a fișa o listă cu toate categoriile.");

        Scanner scan = new Scanner(System.in);

        String categorie = scan.nextLine();
        if(categorie.equalsIgnoreCase("all"))
        {
            for(String s : memorie.getCategorii())
            {
                System.out.print("["+s+"]");
            }
            System.out.println("\n");
            dupa_categorie(memorie);
        }else {
            try {
                cautaCategorie(memorie, categorie);

            } catch (CatInexistenta e) {

                System.out.println("Eroare de căutare: " + e.getMessage());
                System.out.println("Dacă doriți să încercați din nou, introduceți tasta \"Y\" ");

                Scanner scan2 = new Scanner(System.in);
                if (scan2.nextLine().equalsIgnoreCase("Y")) {
                    dupa_categorie(memorie);
                }
            }

        }
    }


    /**
     * Segmentează catalogul de cărți în două liste distincte și le afișează separat. O listă reține date despre cărțile disponibile efectiv în bibliotecă, cealaltă se ocupă de cărțile care sunt în posesia unui client la momentul actual (adică împrumutate).
     * @param memorie instanța care furnizează acces la dicționarul bibliotecii
     */
    public void identifica_books(ManagerMemorie memorie)
    {
        List<Carte> cartiDisponibile = new ArrayList<>();
        List<Carte> cartiImprumutate = new ArrayList<>();

        for(String key : memorie.getBiblioteca().keySet())
        {
            for(Carte c : memorie.getBiblioteca().get(key))
            {
                if(c.isEsteDisponibila() == true)
                {
                    cartiDisponibile.add(c);
                }else{
                    cartiImprumutate.add(c);
                }
            }
        }

        System.out.println("Cărțile disponibile sunt: ");
        for(Carte c : cartiDisponibile)
        {
            System.out.println("    >"+c.toString());
        }
        System.out.println("\n\n");

        System.out.println("Cărțile indisponibile (împrumutate) sunt: ");
        for(Carte c : cartiImprumutate)
        {
            System.out.println("    >"+c.toString());
        }
        System.out.println("\n\n");

    }


    /**
     * FIltrează după un titlu primit de la utilizator. Ignoră diferențele de majuscule.
     * @param memorie instanța care furnizează acces la dicționarul bibliotecii
     */
    public void dupa_titlu(ManagerMemorie memorie)
    {
        System.out.print("Introduceți titlul: ");
        Scanner scanner = new Scanner(System.in);
        String titlu = scanner.nextLine();
        boolean esteGasit = false;
        for(String key : memorie.getBiblioteca().keySet())
        {
            for(Carte c : memorie.getBiblioteca().get(key))
            {
                if(titlu.equalsIgnoreCase(c.getTitlu()))
                {
                    esteGasit = true;
                    System.out.print("\nCartea/cărțile cu acel titlu: ");
                    System.out.println(c.toString());

                }
            }
        }

        if(esteGasit == false)
        {
            System.out.println("\nNu am găsit o carte cu acest titlu.");
        }
    }


    /**
     * Filtrează cărțile după un an introdus de utilizator.
     * Erorile de formatare textuală sunt capturate prin {@link NumberFormatException}
     * @param memorie instanța care furnizează acces la dicționarul bibliotecii
     */
    public void dupa_an(ManagerMemorie memorie)
    {
        System.out.print("Introduceți anul dorit: ");
        Scanner scan = new Scanner(System.in);
        String raspuns = scan.nextLine();
        int an = 0;
        boolean esteGasit = false;

        try {
            an = Integer.parseInt(raspuns);
        } catch (NumberFormatException e) {
            System.out.println("Introduceți o valoare numerică validă!");
            dupa_an(memorie);
            return;
        }

        List<Carte> carteCautata = new ArrayList<>();
        for(String key : memorie.getBiblioteca().keySet()) {

            for (Carte c : memorie.getBiblioteca().get(key)) {
                if(c.getAn_publicare() == an)
                {
                    carteCautata.add(c);
                    esteGasit = true;

                }
            }
        }
        if(esteGasit == true)
        {
            System.out.println("\nCărți din anul " + an + ": ");
            for(Carte c : carteCautata)
            {
                System.out.println("    >" + c.toString());
            }
        }else{
            System.out.println("\nNu am găsit cărți publicate în anul " + an);
        }
    }


    /**
     * Procesează submeniul de căutare avansată, delegând execuția către {@link #dupa_titlu(ManagerMemorie)} sau {@link #dupa_an(ManagerMemorie)} în funcție de selecție.
     * Protejează fluxul captând  excepția {@link NumberFormatException} la citirea deciziei.
     * @param memorie  memorie instanța care furnizează acces la dicționarul bibliotecii
     * @return {@code true} dacă căutarea s-a realizat cu succes, sau {@code false} dacă utilizatorul a ales să se întoarcă
     */
    public boolean filtrare(ManagerMemorie memorie)
    {
        OptiuniComune optC = new OptiuniComune();
        optC.submeniuOpt4();
        Scanner scan = new Scanner(System.in);

        String raspuns = scan.nextLine();
        int optiune = -1;

        try {
            optiune = Integer.parseInt(raspuns);
        } catch (NumberFormatException e) {
            System.out.println("Introduceți un număr valid din lista de opțiuni.");
            return filtrare(memorie);
        }



        switch (optiune){
            case 1:
                dupa_titlu(memorie);
                return true;
            case 2:
                dupa_an(memorie);
                return true;
            case 3:
                return false;

            default:
                System.out.println("Vă rugăm să alegeți o opțiune validă.");

                return filtrare(memorie);
        }



    }


}
