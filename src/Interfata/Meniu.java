package Interfata;

import Actiuni.*;
import ObiecteReale.Utilizator;

import java.util.Scanner;

/**
 * Clasa centrală a interfeței cu utilizatorul, având rolul de a orchestra navigația între diferitele opțiuni și submeniuri ale aplicației.
 */
public class Meniu {

    private Utilizator utilizator;
    private SalveazaInFisiere salveaza;

    /**
     * Declanșează închiderea controlată a sistemului, apelând metoda {@link SalveazaInFisiere#salveazaTOT()} pentru a asigura persistența datelor din memoria volatilă pe disc.
     *
     * @param memorie managerul de memorie ale cărui date trebuie salvate
     */
    public void exit(ManagerMemorie memorie)
    {
        salveaza = new SalveazaInFisiere(memorie);
        salveaza.salveazaTOT();
    }

    public Meniu() {
        this.utilizator = new Utilizator();
    }


    /**
     * Pornește ciclul de viață al meniului, delegând procesul inițial către {@link Logare#intrebareLogare(ManagerMemorie, Utilizator)} și preluând rolul stabilit pentru configurarea sesiunii.
     *
     * @param memorie instanța care conține colecțiile de date ale bibliotecii
     *
     */
    public void startMeniu(ManagerMemorie memorie) {
        Logare logare = new Logare();
        logare.intrebareLogare(memorie, utilizator);
        if(logare.esteBibliotecar == false)
        {
            utilizator.setEsteBibliotecar(false);
        }else{
            utilizator.setEsteBibliotecar(true);
        }


    }

    /**
     * Afișează panoul principal de opțiuni (bazându-se pe nivelul de acces), apelând metoda {@link OptiuniComune#afisarePrincipala(boolean)} cu permisiunile profilului curent.
     */
    public void prezentareOptiuni()
    {
        OptiuniComune comun = new OptiuniComune();
        comun.afisarePrincipala(utilizator.getEsteBibliotecar());

    }

    /**
     * Gestionează pauza de navigare după finalizarea unei acțiuni.
     * Așteaptă confirmarea utilizatorului prin introducerea caracterului "Y" pentru a reafişa opțiunile prin apelul {@link #prezentareOptiuni()} (altfel se apelează pe sine recursiv).
     */
    public void revii()
    {
        System.out.println("Pentru a reveni la meniul principal, introduceți tasta \"Y\"");


            Scanner scan = new Scanner(System.in);
            String raspuns = scan.nextLine();
            if (raspuns.equalsIgnoreCase("y")) {
                prezentareOptiuni();
            } else {
                revii();
            }


    }


    /**
     * Preia comanda numerică de la utilizator și direcționează execuția către acțiunea corespunzătoare din pachetul logic.
     * Dacă acțiunea necesită o pauză vizuală la finalizare, se declanșează metoda {@link #revii()}, altfel
     * se revine direct la panoul principal prin {@link #prezentareOptiuni()}.
     *
     * @param memorie referința către colecțiile de date necesare îndeplinirii comenzii
     * @throws NumberFormatException dacă inputul citit din consolă nu poate fi transformat într-o valoare de tip {@code int}
     */
    public void selectie(ManagerMemorie memorie)
    {
        OptiuniComune optC = new OptiuniComune();
        OptiuniNormal optN = new OptiuniNormal();
        Scanner scan = new Scanner(System.in);
        String rasp = scan.nextLine();
        int optiune = -1;
        try {
            optiune = Integer.parseInt(rasp);
        } catch (NumberFormatException e) {
            System.out.println("Introduceți un număr valid din lista de opțiuni!! Răspunusl dvs:");
            selectie(memorie);
            return;
        }
        ActiuniComune actC = new ActiuniComune();


        switch (optiune){
            case 0:
                actC.afiseazaTOT(memorie);
                revii();
                selectie(memorie);
                break;
            case 1:
                actC.dupa_autor(memorie);
                revii();
                selectie(memorie);
                break;
            case 2:
                actC.dupa_categorie(memorie);
                revii();
                selectie(memorie);
                break;
            case 3:
                actC.identifica_books(memorie);
                revii();
                selectie(memorie);
                break;
            case 4:
                boolean g = actC.filtrare(memorie);
                if (g == true)
                {
                    revii();
                }else{
                    prezentareOptiuni();
                }

                selectie(memorie);
                break;
            case 5:
                if(utilizator.getEsteBibliotecar() == false)
                {
                    optN.afisareImprumutare();



                    ActiuniNormal actN = new ActiuniNormal(memorie);
                    actN.imprCentraliz(utilizator);
                    prezentareOptiuni();
                    selectie(memorie);

                }else {

                    ActiuniBibliotecar actB = new ActiuniBibliotecar();
                    actB.afiseazaImprumuturiPeData(memorie);
                    revii();
                    selectie(memorie);
                }

                break;
            case 6:
                if(utilizator.getEsteBibliotecar() == false)
                {
                    ActiuniNormal actN = new ActiuniNormal(memorie);
                    actN.showOwnB(memorie, utilizator);
                    revii();
                    selectie(memorie);

                }else {
                    ActiuniBibliotecar actB = new ActiuniBibliotecar();
                    actB.meniuRapoarte(memorie);
                    prezentareOptiuni();
                    selectie(memorie);
                }
                break;
            case 7:
                if(utilizator.getEsteBibliotecar() == false)
                {
                    ActiuniNormal actN = new ActiuniNormal(memorie);
                    actN.restituieCarte(utilizator);
                    revii();
                    selectie(memorie);
                }else {

                    ActiuniBibliotecar actB = new ActiuniBibliotecar();
                    actB.adaugaCarte(memorie);
                    revii();
                    selectie(memorie);
                }
                break;
            case 8:
                this.utilizator = new Utilizator();
                startMeniu(memorie);
                prezentareOptiuni();
                selectie(memorie);
                break;
            case 9:
                exit(memorie);
                break;
            default:
                System.out.println("Vă rugăm să alegeți o opțiune validă.");
                prezentareOptiuni();
                selectie(memorie);
                break;
        }

        System.out.println("============================================================================================================================");
    }

}
