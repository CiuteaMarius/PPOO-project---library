package Actiuni;

import ObiecteReale.Carte;
import ObiecteReale.Imprumut;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;

public class ActiuniBibliotecar {


    private LocalDate cereData() {
        Scanner scan = new Scanner(System.in);

        while (true) {
            System.out.println("Introduceți data pentru care doriți să vedeți împrumuturile.");
            System.out.println("Folosiți formatul AAAA-LL-ZZ (exemplu: 2026-10-26): ");
            String data = scan.nextLine();

            try {

                return LocalDate.parse(data);
            } catch (DateTimeParseException e) {

                System.out.println("Formatul datei este incorect! Vă rugăm să respectați formatul AAAA-LL-ZZ.\n");
            }
        }
    }

    public void afiseazaImprumuturiPeData(ManagerMemorie memorie) {


        LocalDate dataCautata = cereData();

        System.out.println("\nÎmprumuturile efectuate la data de " + dataCautata + " sunt:");
        boolean esteGasit = false;

        for (String numeUser : memorie.getImprumuturi().keySet()) {
            List<Imprumut> listaImprumuturi = memorie.getImprumuturi().get(numeUser);

            for (Imprumut imp : listaImprumuturi) {
                if (imp.getData().toLocalDate().equals(dataCautata)) {

                    esteGasit = true;
                    String[] bucati = imp.getIdCarte().split("!#!");
                    String titlu = bucati[0];
                    String autor = bucati[1];

                    System.out.println(" -> Utilizatorul [" + numeUser + "] a împrumutat cartea: " + titlu + " de " + autor);
                }
            }
        }

        if (esteGasit == false) {
            System.out.println("Niciun împrumut nu a fost înregistrat în această zi.");
        }
    }



    private int cereAn() {
        Scanner scan = new Scanner(System.in);
        while (true) {
            System.out.println("Introduceți anul publicării:");
            String input = scan.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Eroare! Anul trebuie să fie un număr întreg valid. Încercați din nou.\n");
            }
        }
    }


    public void adaugaCarte(ManagerMemorie memorie) {
        Scanner scan = new Scanner(System.in);

        System.out.println("Introduceți titlul cărții:");
        String titlu = scan.nextLine().trim();

        System.out.println("Introduceți numele autorului:");
        String autor = scan.nextLine().trim();


        int an = cereAn();

        System.out.println("Introduceți categoria din care face parte cartea (ex: Beletristică, Informatică):");
        String categorie = scan.nextLine();


        Carte carteNoua = new Carte(titlu, autor, an, true, categorie);


        if (memorie.getBiblioteca().containsKey(categorie)) {

            memorie.getBiblioteca().get(categorie).add(carteNoua);
        } else {

            memorie.getBiblioteca().put(categorie, new ArrayList<>());
            memorie.getBiblioteca().get(categorie).add(carteNoua);
            memorie.getCategorii().add(categorie);
        }

        System.out.println("\nCartea \"" + titlu + "\" a fost adăugată cu succes în bibliotecă!");
    }

    //RAPOARTE:
    private void raportAutor(ManagerMemorie memorie, String autor)
    {
        SalveazaInFisiere save = new SalveazaInFisiere(memorie);

        List<Carte> carti = new ArrayList<>();
        int totalCarti = 0;     //cate carti a scris in total acest autor



        for(List<Carte> cartisoare : memorie.getBiblioteca().values())
        {
            for(Carte c : cartisoare)
            {
                if(c.getAutor().equalsIgnoreCase(autor))
                {
                    carti.add(c);
                    totalCarti++;
                }
            }
        }

        int[] modulCategorii = new int[totalCarti];
        for(int i = 0; i < totalCarti; i++)
        {
            int aparitie = 0;
            String category = carti.get(i).getCategorie();
            for(Carte c : carti)
            {

                if(c.getCategorie().equalsIgnoreCase(category)){
                    aparitie++;
                }

            }
            modulCategorii[i] = aparitie;
        }

        //deci pt fiecare carte din "Carti" avem un corespondent in "modulCategorii" care arata de cate ori a aparut acea categoria acelei carti in publicatiile autorului.

        int maxim = modulCategorii[0];
        for(int i = 0; i<totalCarti-1; i++)
        {

           if(maxim < modulCategorii[i+1]){ maxim = modulCategorii[i+1];}
        }

        Set<String> categorii_preferate = new HashSet<>();  //pt ca set nu permite duplicate, vom avea o colectie de date curata
        for(int i =0; i < totalCarti; i++)
        {
            if(modulCategorii[i] == maxim)
            {
                categorii_preferate.add(carti.get(i).getCategorie());
            }
        }

        StringBuilder sb = new StringBuilder();
        sb.append("În baza de date a bibliotecii avem un total de ").append(totalCarti).append(" cărți scrise de autorul ").append(autor).append(". \n");
        if(totalCarti == 1)
        {
            sb.append("Aceasta este: ").append(carti.get(0).getTitlu()).append(" și a fost publicată în anul ").append(carti.get(0).getAn_publicare()).append(".\n");
            sb.append("Aceasta face parte din categoria ").append("[").append(carti.get(0).getCategorie()).append("].\n");
        }else{
            sb.append("Acestea sunt:\n");
            for(Carte c : carti)
            {
                sb.append("     ").append(c.toStringLite2()).append("\n");
            }

            if(categorii_preferate.size()==1)
            {
                sb.append("În baza noastră de date, acest autor a scris cel mai mult în categoria ");
                for(String cat : categorii_preferate)
                {
                    sb.append("[").append(cat).append("]");
                }
                sb.append(". Am găsit ").append(maxim).append(" lucrări scrise de ").append(autor).append(" din această categorie.");
            }else if(categorii_preferate.size() == 2)
            {
                sb.append("În baza noastră de date, acest autor a scris cel mai mult în categoriile ");
                for(String cat : categorii_preferate)
                {
                    sb.append("[").append(cat).append("] și ");
                }
                sb.delete(sb.length()-4, sb.length());  //sterg ultimele caractere ca sa nu apara un "si" la final.
                sb.append(". Am găsit ").append(maxim).append(" lucrări scrise de ").append(autor).append(" din fiecare dintre aceste două categorii.");
            }
            else{
                sb.append("În baza noastră de date, acest autor a scris cel mai mult în categoriile ");
                for(String cat : categorii_preferate)
                {
                    sb.append("[").append(cat).append("]\n");
                }

                sb.append("Am găsit ").append(maxim).append(" lucrări scrise de ").append(autor).append(" din fiecare dintre aceste categorii.");
            }
        }

        String rezultat = sb.toString();
        String titlu = "RAPORT CU TOATE CĂRȚILE SCRISE DE " + autor.toUpperCase();
        save.saveRaport(titlu, rezultat, "Raport autor");



    }

    private void raportCategorii(ManagerMemorie memorie, String categorie)
    {
        SalveazaInFisiere salveaza = new SalveazaInFisiere(memorie);
        List<Carte> carti = memorie.getBiblioteca().get(categorie);
        int total = carti.size();

        StringBuilder sb = new StringBuilder();
        sb.append("Din categoria ").append(categorie).append(" avem ");
        if(carti.size() == 1)
        {
            sb.append(" o carte. Aceasta este: ").append(carti.get(0).toStringLite()).append("\n");

        }else{
            sb.append(total).append(" cărți. Acestea sunt:");
            for(Carte c : carti)
            {
                sb.append("     ").append(c.toStringLite()).append("\n");
            }
        }

        String rezultat = sb.toString();
        String titlu = "RAPORT PENTRU CATEGORIA" + categorie.toUpperCase();
        salveaza.saveRaport(titlu, rezultat, "Raport categorie");

    }

    private void raportImprumuturi(ManagerMemorie memorie, LocalDate data)
    {
        SalveazaInFisiere salveaza = new SalveazaInFisiere(memorie);
        StringBuilder sb1 = new StringBuilder();

        int total = 0;
        sb1.append("În această dată, utilizatorii au împrumutat cărți după cum urmează:\n");
        for (String numeUser : memorie.getImprumuturi().keySet()) {
            List<Imprumut> allBorrowed = memorie.getImprumuturi().get(numeUser);   //toate imprumuturile userului curent din bucla
            List<Imprumut> searchedB = new ArrayList<>();           //imprumuturile userului curent din bucla care de asemenea se intampla sa aiba data care coincide cu data cautata

            for (Imprumut imp : allBorrowed) {
                if (imp.getData().toLocalDate().equals(data)) {
                    searchedB.add(imp);
                }
            }


            int inapoiate = 0;
            if(searchedB.size() >= 1)
            {
                sb1.append(numeUser).append(" a  împrumutat ").append(searchedB.size()).append(" cărți:\n");
                for(Imprumut impr : searchedB)
                {
                    String[] parti =impr.getIdCarte().split("!#!");
                    String titlu = parti[0];

                    sb1.append("        [").append(titlu).append("] - ");

                    if(impr.isEsteActiv() == true){sb1.append("Încă nu a fost înapoiată\n");}else{sb1.append("A fost înapoiată la data de ").append(impr.getDataRestituire().toLocalDate()).append("\n"); inapoiate++;}
                }

                sb1.append(numeUser).append(" a restituit ").append(inapoiate).append(" din totalul de cărți împrumutate. \n");
                float procent = (100.0f * inapoiate)/searchedB.size();
                sb1.append("Cu alte cuvinte, a restituit ").append(procent).append("% din totalul de cărți împrumutate. \n\n");



            }

            total = total + searchedB.size();




        }

        sb1.append("În total, în această dată au fost înregistrate ").append(total).append(" împrumuturi.");
        String rezultat = sb1.toString();
        String titlu = "ÎMPRUMUTURILE EFECTUATE LA DATA: " + data;
        salveaza.saveRaport(titlu, rezultat, "Data specifica");

    }

}
