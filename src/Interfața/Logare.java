package Interfața;

import Actiuni.ManagerMemorie;
import ObiecteReale.Utilizator;

import java.util.Scanner;

public class Logare {

    protected boolean esteBibliotecar;
    public void intrebareLogare(ManagerMemorie memorie, Utilizator user)
    {
        System.out.println("Bună ziua! Cum doriți să vă autentificați? Optiuni: \n >1 utilizator normal \n sau \n >2 bibliotecar");
        separareLogare(memorie, user);

    }

    public void separareLogare(ManagerMemorie memorie, Utilizator user)
    {

        Scanner scan = new Scanner(System.in);
        int optiune = scan.nextInt();
        if(optiune == 1)
        {
            esteBibliotecar = false;
            System.out.println("Introduceți numele de utilizator.");
            Scanner scan2 = new Scanner(System.in);
            String username = scan2.nextLine();
            user.setUsername(username);
            memorie.adaugaUtilizator(user);



            System.out.println(username + ", v-ați logat cu succes în rolul de utilizator normal!\n");
            scan.nextLine();

        } else if (optiune == 2) {
            esteBibliotecar = true;
            System.out.println("Logat cu succes în rolul de bibliotecar!\n");
            scan.nextLine();

        }else {
            System.out.println("Vă rugăm să alegeți una dintre opțiunile prezentate. Alegeți 1 pentru autentificarea ca utilizator normal. Alegeți 2 pentru autentificarea ca bibliotecar\n");
            separareLogare(memorie, user);
        }
    }
}
