package Interfata;

import Actiuni.ManagerMemorie;
import Exceptii.OptiuneInvalida;
import ObiecteReale.Utilizator;

import java.util.Scanner;

/**
 * Gestionează procesul de autentificare a utilizatorilor în sistem.
 * Separă fluxul de execuție în funcție de rolul ales, stabilind drepturile
 * de acces pentru sesiunea curentă.
 */
public class Logare {

    protected boolean esteBibliotecar;

    /**
     * Afișează mesajul de întâmpinare și solicită utilizatorului să își aleagă rolul.
     * @param memorie Instanța care gestionează starea curentă a datelor din bibliotecă.
     * @param user Obiectul în care se vor salva datele profilului proaspăt conectat.
     */
    public void intrebareLogare(ManagerMemorie memorie, Utilizator user)
    {
        System.out.println("Bună ziua! Cum doriți să vă autentificați? Optiuni: \n >1 utilizator normal \n sau \n >2 bibliotecar");
        separareLogare(memorie, user);

    }

    /**
     * Prelucrează selecția utilizatorului preluată de la consolă și finalizează autentificarea.
     * Dacă rolul este de client normal, adică opțiunea {@code 1}, înregistrează noul nume în memorie.
     *
     * @param memorie instanța care gestionează starea curentă a datelor din bibliotecă
     * @param user obiectul în care se vor asocia datele profilului conectat
     * @throws NumberFormatException dacă valoarea introdusă de la tastatură pentru selecția meniului nu este un număr valid
     * @throws OptiuneInvalida dacă valoarea numerică introdusă este diferită de {@code 1} sau {@code 2}
     */
    public void separareLogare(ManagerMemorie memorie, Utilizator user)
    {

        Scanner scan = new Scanner(System.in);
        String input = scan.nextLine().trim();
        int optiune = -1;

        try {
            optiune = Integer.parseInt(input);
            if (optiune != 1 && optiune != 2) {
                throw new OptiuneInvalida("Vă rugăm alegeți 1 pentru utilizator normal sau 2 pentru bibliotecar.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Eroare: Trebuie să introduceți o cifră (1 sau 2).");
            separareLogare(memorie, user);
            return;
        } catch (OptiuneInvalida e) {
            System.out.println(e.getMessage());
            separareLogare(memorie, user);
            return;
        }

        if (optiune == 1) {
            esteBibliotecar = false;
            System.out.println("Introduceți numele de utilizator.");
            String username = scan.nextLine();
            user.setUsername(username);
            memorie.adaugaUtilizator(user);
            System.out.println(username + ", v-ați logat cu succes în rolul de utilizator normal!\n");
        } else if (optiune == 2) {
            esteBibliotecar = true;
            System.out.println("Logat cu succes în rolul de bibliotecar!\n");
        }
    }
}
