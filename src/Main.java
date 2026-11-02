import Actiuni.ManagerMemorie;
import Interfata.Meniu;
/**
 * Punctul de intrare în aplicația de gestiune a bibliotecii.
 * Această clasă orchestrează inițializarea sistemului, încărcarea datelor
 * persistente din fișiere și delegarea controlului către interfața cu utilizatorul.
 */
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