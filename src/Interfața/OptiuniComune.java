package Interfața;

public class OptiuniComune {
    public void afisarePrincipala(boolean isBibliotecar)
    {
        System.out.println("============================================================================================================================");
        System.out.print("Meniu: " +
                "\n     |\n     |__  0) Afișează toate cărțile bibliotecii" +
                "\n     |\n     |__  1) Afișează toate cărțile scrise de un anumit autor" +
                "\n     |\n     |__  2) Afișează cărțile dintr-o anumită categorie" +
                "\n     |\n     |__  3) Identifică cărțile disponibile sau împrumutate" +
                "\n     |\n     |__  4) Caută o carte după un alt criteriu");
        if (isBibliotecar == true)
        {
            OptiuniBibliotecar o1 = new OptiuniBibliotecar();
            o1.afisarePrincipala();


        }else{
            OptiuniNormal o2 = new OptiuniNormal();
            o2.afisarePrincipala();

        }
        System.out.println("\n     |\n     |__  8) Delogare");
        System.out.println("     |\n     |__  9) Închide aplicația");
        System.out.println("\nRaspunsul dvs: ");

    }
    public void submeniuOpt4()
    {
        System.out.println("Criteriile disponibile pentru căutare sunt TITLUL CĂRȚII și ANUL PUBLICĂRII.");
        System.out.println("Pentru a căuta cartea folosind titlul, introduceți valoarea 1. Pentru a căuta cartea folosind anul publicării, introduceți valoarea 2. Pentru revenirea la meniul principal, apasati 3.");
        System.out.print("Răspunsul dvs:");


    }


}
