package Actiuni;

import Excepții.CatInexistenta;
import Interfața.OptiuniComune;
import ObiecteReale.Carte;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AcțiuniComune {

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

    public void dupa_an(ManagerMemorie memorie)
    {
        System.out.print("Introduceți anul dorit: ");
        Scanner scan = new Scanner(System.in);
        int an = 0;
        boolean esteGasit = false;
        if(scan.hasNextInt())
        {
            an = scan.nextInt();
            scan.nextLine();

        }else{
            System.out.println("Introduceți o valoare numerică!");
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

    public boolean filtrare(ManagerMemorie memorie)
    {
        OptiuniComune optC = new OptiuniComune();
        optC.submeniuOpt4();
        Scanner scan = new Scanner(System.in);


        int optiune = -1;
        if(scan.hasNextInt())
        {
            optiune = scan.nextInt();
            scan.nextLine();

        }else{
            System.out.println("Introduceți un număr din lista de opțiuni.");
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
