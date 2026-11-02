package Actiuni;

import Exceptii.AnInvalid;
import Exceptii.OptiuneInvalida;
import ObiecteReale.Carte;
import ObiecteReale.Imprumut;
import ObiecteReale.Utilizator;

import java.util.*;


/**
 * Centralizează operațiunile destinate exclusiv clienților obișnuiți.
 * Conține logica necesară pentru finalizarea împrumuturilor, consultarea istoricului propriu și restituirea lucrărilor.
 */
public class ActiuniNormal {

    private ActiuniComune actC;
    private ManagerMemorie memorie;

    /**
     * Stabilește legătura dintre logica utilizatorului și sursa centrală de date din RAM.
     *
     * @param memorie instanța care furnizează acces la dicționarul bibliotecii
     */
    public ActiuniNormal(ManagerMemorie memorie) {
        this.memorie = memorie;
    }


    /**
     * Formează cheia unică a cărții și actualizează starea acesteia în memorie la momentul împrumutului.
     * @param carte obiectul selectat care devine indisponibil pe raft
     * @param user profilul clientului căruia i se asociază tranzacția
     */
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
                return c;
            }
        }

        return null;
    }


    /**
     * Gestionează fluxul complet de împrumut. Solicită clarificări suplimentare (autor și an) prin intermediul metodei {@link #cautaCarteExacta(List, String, int)} dacă cumva există mai multe cărți cu același titlu.
     * @param user clientul inițiator al tranzacției
     * @throws NumberFormatException dacă anul introdus nu poate fi parsat ca număr întreg
     * @throws AnInvalid dacă {@code year} este negativ sau depășește anul calendaristic curent
     */
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
                int anCurent = java.time.LocalDate.now().getYear();
                if (year < 0 || year > anCurent) {
                    throw new AnInvalid("Anul publicării introdus nu este valid calendaristic.");
                }
                carteDeImprumutat = cautaCarteExacta(cartiGasite, author, year);
            } catch (NumberFormatException e) {
                System.out.println("Anul introdus este invalid.");
                return;
            } catch (AnInvalid e) {
                System.out.println(e.getMessage());
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


    /**
     * Parcurge istoricul tranzacțiilor și tipărește exclusiv lucrările reținute de persoana conectată.
     * @param memorie instanța care furnizează acces la dicționarul de împrumuturi
     * @param user utilizatorul a cărui activitate se dorește a fi consultată
     */
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


    /**
     * Permite clientului să înapoieze o carte împrumutată. Transformă starea împrumutului în inactivă și marchează cartea ca fiind disponibilă.
     * @param user clientul care realizează restituirea
     *  @throws NumberFormatException dacă opțiunea numerică aleasă din listă este invalidă ca format
     */
    public void restituieCarte(Utilizator user) {

        if (memorie.getImprumuturi().containsKey(user.getUsername()) == false) {
            System.out.println("Nu aveți niciun împrumut înregistrat.");
            return;
        }

        List<Imprumut> toateImprumuturile = memorie.getImprumuturi().get(user.getUsername());
        List<Imprumut> imprumuturiActive = new ArrayList<>();


        for (Imprumut imp : toateImprumuturile) {
            if (imp.isEsteActiv() == true) {
                imprumuturiActive.add(imp);
            }
        }

        if (imprumuturiActive.isEmpty()) {
            System.out.println("Nu aveți cărți împrumutate în acest moment (sau le-ați restituit pe toate).");
            return;
        }


        System.out.println("Cărțile împrumutate de dumneavoastră sunt:");
        int contor = 1;
        for (Imprumut imp : imprumuturiActive) {
            String[] bucati = imp.getIdCarte().split("!#!");
            String titlu = bucati[0];
            String autor = bucati[1];
            String an = bucati[2];

            System.out.println(contor + ". " + titlu + " de " + autor + " în " + an);
            contor = contor + 1;
        }

        System.out.println("\nIntroduceți numărul cărții pe care doriți să o restituiți sau tastați 'Y' pentru anulare.");
        Scanner scan = new Scanner(System.in);
        String raspuns = scan.nextLine().trim();

        if (raspuns.equalsIgnoreCase("y")) {
            System.out.println("Proces anulat.");
            return;
        }


        int selectie = -1;
        try {
            selectie = Integer.parseInt(raspuns);
        } catch (NumberFormatException e) {
            System.out.println("Opțiune invalidă. Trebuie să introduceți un număr sau 'Y'.");
            return;
        }

        if (selectie < 1 || selectie > imprumuturiActive.size()) {
            System.out.println("Numărul introdus nu corespunde niciunei cărți din listă.");
            return;
        }


        Imprumut imprDeRestituit = imprumuturiActive.get(selectie - 1);
        imprDeRestituit.setEsteActiv(false);
        imprDeRestituit.setDataRestituire(java.time.LocalDateTime.now());


        String idTarget = imprDeRestituit.getIdCarte();
        for (List<Carte> listaCarti : memorie.getBiblioteca().values()) {
            for (Carte c : listaCarti) {
                String idCurent = c.getTitlu() + "!#!" + c.getAutor() + "!#!" + c.getAn_publicare();
                if (idCurent.equalsIgnoreCase(idTarget)) {
                    c.setEsteDisponibila(true);
                }
            }
        }

        System.out.println("Cartea a fost restituită cu succes!");
    }
}

