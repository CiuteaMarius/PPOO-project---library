package Interfața;

import Actiuni.ActiuniNormal;
import Actiuni.ManagerMemorie;
import Actiuni.SalveazaInFisiere;
import ObiecteReale.Utilizator;
import Actiuni.AcțiuniComune;

import java.util.Scanner;

public class Meniu {

    private Utilizator utilizator;
    private SalveazaInFisiere salveaza;
    public void exit(ManagerMemorie memorie)
    {
        salveaza = new SalveazaInFisiere(memorie);
        salveaza.salveazaTOT();
    }

    public Meniu() {
        this.utilizator = new Utilizator();
    }

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

    public void prezentareOptiuni()
    {
        OptiuniComune comun = new OptiuniComune();
        comun.afisarePrincipala(utilizator.getEsteBibliotecar());

    }

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

    public void selectie(ManagerMemorie memorie)
    {
        OptiuniComune optC = new OptiuniComune();
        OptiuniNormal optN = new OptiuniNormal();
        Scanner scan = new Scanner(System.in);
        int optiune = -1;
        if(scan.hasNextInt())
        {
            optiune = scan.nextInt();
            scan.nextLine();

        }else{
            System.out.println("Introduceți un număr din lista de opțiuni.");
            selectie(memorie);
        }
        AcțiuniComune actC = new AcțiuniComune();


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
                    Actiuni.ActiuniNormal actiuniNormal = new Actiuni.ActiuniNormal(memorie);


                    ActiuniNormal actN = new ActiuniNormal(memorie);
                    actN.imprCentraliz(utilizator);
                    prezentareOptiuni();
                    selectie(memorie);
                    break;
                }
            case 8:
                startMeniu(memorie);
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
