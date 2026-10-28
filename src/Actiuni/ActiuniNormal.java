package Actiuni;

import Excepții.OptiuneInvalida;
import ObiecteReale.Carte;
import ObiecteReale.Imprumut;
import ObiecteReale.Utilizator;

import java.util.*;

public class ActiuniNormal {

    private AcțiuniComune actC;
    private ManagerMemorie memorie;

    public ActiuniNormal(ManagerMemorie memorie) {
        this.memorie = memorie;
    }

    public void imprumuta(Carte carte, Utilizator user)
    {
        String idCarte = carte.getTitlu() + "!#!" + carte.getAutor() + "!#!" + carte.getAn_publicare();
        Imprumut imprumut = new Imprumut();
        imprumut.setIdCarte(idCarte);

        memorie.adaugaImprumut(user.getUsername(), imprumut);

        carte.setEsteDisponibila(false);

    }

    private String askTitle(){
        System.out.println("Scrie titlul cărții: ");
        Scanner scan = new Scanner(System.in);
        return scan.nextLine();
    }
    private String askAuthor(){
        System.out.println("Scrie autorul cărții: ");
        Scanner scan = new Scanner(System.in);
        return scan.nextLine();
    }
    private String askYear(){
        System.out.println("Scrie data publicării cărții: ");
        Scanner scan = new Scanner(System.in);
        return scan.nextLine();
    }

    private List<Carte> carteFinder(String titlu) {
        List<Carte> gasite = new ArrayList<>();

        for (List<Carte> listaCarti : memorie.getBiblioteca().values()) {
            for (Carte c : listaCarti) {
                if (titlu.equalsIgnoreCase(c.getTitlu()) && c.isEsteDisponibila() == true) {
                    gasite.add(c);
                }
            }
        }
        return gasite;
    }
    private Carte cautaCarteExacta(List<Carte> cartiCuAcelasiTitlu, String autor, int an) {
        for (Carte c : cartiCuAcelasiTitlu) {
            if (c.getAutor().equalsIgnoreCase(autor) && c.getAn_publicare() == an) {
                return c; // Am găsit-o!
            }
        }
        // Abia DUPĂ ce se termină bucla și nu am găsit nimic, returnăm null
        return null;
    }



    public void imprCentraliz(Utilizator user) {

        String title = askTitle();
        List<Carte> cartiGasite = carteFinder(title);
        Carte carteDeImprumutat = null;

        if (cartiGasite.isEmpty()) {
            System.out.println("Nu există nicio carte cu acest titlu.");
            return;
        }

        if (cartiGasite.size() == 1) {
            carteDeImprumutat = cartiGasite.get(0);
        } else {
            System.out.println("\nAm găsit mai multe cărți cu acest titlu. Vă rugăm să oferiți detalii suplimentare.");
            String author = askAuthor();
            String yearText = askYear();

            try {
                int year = Integer.parseInt(yearText);
                carteDeImprumutat = cautaCarteExacta(cartiGasite, author, year);
            } catch (NumberFormatException e) {
                System.out.println("Anul introdus este invalid.");
                return;
            }
        }

        if (carteDeImprumutat == null) {
            System.out.println("Cartea cu titlul '" + title + "' nu are acest autor sau această dată a publicării.");
            return;
        }

        System.out.println("Cartea selectată a fost scrisă de " + carteDeImprumutat.getAutor() + " în anul " + carteDeImprumutat.getAn_publicare());

        Scanner scan = new Scanner(System.in);
        while(true) {
            System.out.println("Apăsați 1 pentru a continua împrumutul sau Y pentru anulare.");
            String raspuns = scan.nextLine().trim();

            try {
                if(raspuns.equals("1")) {
                    imprumuta(carteDeImprumutat, user);
                    System.out.println("Împrumutul a fost înregistrat cu succes!");
                    return;
                } else if(raspuns.equalsIgnoreCase("y")) {
                    System.out.println("Proces anulat.");
                    return;
                } else {
                    throw new OptiuneInvalida("Tastă incorectă! Vă rugăm să introduceți doar '1' sau 'Y'.");
                }
            } catch (OptiuneInvalida e) {
                System.out.println("Eroare: " + e.getMessage() + "\n");
            }
        }
    }

    public void showOwnB(ManagerMemorie memorie, Utilizator user)
    {
        if(memorie.getImprumuturi().containsKey(user.getUsername()))
        {
            System.out.println("Cărțile împrumutate de dvs sunt: ");
            for(String key : memorie.getImprumuturi().keySet())
            {
                if (key.equalsIgnoreCase(user.getUsername()))
                {
                    for(Imprumut imprumut : memorie.getImprumuturi().get(key)) {


                        String[] bucati = imprumut.getIdCarte().split("!#!");
                        String titlu = bucati[0];
                        String autor = bucati[1];
                        String an = bucati[2];

                        System.out.println(titlu + " de " + autor + " în " + an);
                    }
                }
            }
        }
        else{
            System.out.println("Nu ați împrumutat cărți");
        }

    }
}

