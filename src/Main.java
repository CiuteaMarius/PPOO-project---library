import Actiuni.ManagerMemorie;
import Actiuni.SalveazaInFisiere;
import Interfața.Meniu;

public class Main {
    public static void main(String[] args) {


        ManagerMemorie memorie = new ManagerMemorie();
        memorie.citesteTXT();
        memorie.readUsers();
        memorie.readBorrowed();


        Meniu meniu = new Meniu();
        meniu.startMeniu(memorie);
        meniu.prezentareOptiuni();
        meniu.selectie(memorie);






    }

}