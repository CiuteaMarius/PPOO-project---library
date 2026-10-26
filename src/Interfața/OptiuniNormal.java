package Interfața;

public class OptiuniNormal {

    public void afisarePrincipala()
    {
        System.out.print("\n     |\n     |__  5) Împrumută o carte" + "\n     |\n     |__  6) Vizualizează propriile cărți" +
                "\n     |\n     |__  7) Restituie o carte împrumutată");
    }
    public void afisareImprumutare()
    {
        System.out.println("Introduceți Titlul Cărții, Numele Autorului și Data publicării pentru a putea identifica exact cartea pe care vreți s-o împrumutați.");
        System.out.println("Dacă doriți să vă întoarceți înapoi la meniul principal, la oricare din aceste câmpuri introduceți litera [y] și apăsați ENTER.");
        System.out.println("Dacă doriți să vedeți din nou lista întreagă de cărți, la oricare dintre aceste campuri introduceți [all] și apăsați ENTER.");
    }

}
