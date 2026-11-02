package Actiuni;

import ObiecteReale.Carte;
import ObiecteReale.Imprumut;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Responsabilă cu persistența datelor la finalul sesiunii de lucru.
 * Preia informațiile stocate temporar în memoria RAM și suprascrie fișierele text pentru a garanta salvarea modificărilor.
 */
public class SalveazaInFisiere {
    private ManagerMemorie memorie;

    /**
     * Inițializează obiectul prin care se efectuează salvările
     *
     * @param memorie instanța care conține colecțiile actualizate pe parcursul rulării
     */
    public SalveazaInFisiere(ManagerMemorie memorie) {
        this.memorie = memorie;

    }

    /**
     * Declanșează secvențial toate procedurile de scriere pe disc.
     */
    public void salveazaTOT()
    {
        salveazaUtilizatorii();
        salveazaImprumuturi();
        salveazaCartile();
    }

    /**
     * Suprascrie fișierul text al utilizatorilor cu datele din colecția curentă.
     *
     * @throws RuntimeException dacă fluxul de scriere este întrerupt brusc
     */
    public void salveazaUtilizatorii()
    {
        try {
            FileWriter fw = new FileWriter("src/Utilizatori.txt");
            BufferedWriter bw = new BufferedWriter(fw);
            for(String s : memorie.getUtilizatori())
            {
                bw.write(s);
                bw.newLine();
            }

            bw.close();
            fw.close();
            System.out.println("Salvare reușită cu succes!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    /**
     * Formatează fiecare tranzacție de împrumut folosind separatorul specific „##” și actualizează fișierul text cu împrumuturi.
     *
     * @throws RuntimeException dacă sistemul refuză permisiunea de scriere a fișierului
     */
    public void salveazaImprumuturi()
    {
        try {
            FileWriter fw = new FileWriter("src/Împrumuturi.txt");
            BufferedWriter bw = new BufferedWriter(fw);

            for (String username : memorie.getImprumuturi().keySet()) {
                List<Imprumut> listaCartiImprumutate = memorie.getImprumuturi().get(username);

                for (Imprumut imprumut : listaCartiImprumutate) {
                    String linie = username + "##" +
                            imprumut.getIdCarte() + "##" +
                            imprumut.getData().toString() + "##" +
                            imprumut.isEsteActiv() + "##" +
                            imprumut.getDataRestituire();

                    bw.write(linie);
                    bw.newLine();
                }
            }

            bw.close();
            fw.close();


        } catch (IOException e) {
            throw new RuntimeException("Eroareeeeeee" + e.getMessage(), e);
        }
    }

    /**
     * Parcurge întregul catalog al bibliotecii și exportă atributele fiecărei lucrări în fișierul text principal.
     * @throws RuntimeException dacă apar erori de accesare a fișierului destinație
     */
    public void salveazaCartile() {
        try {
            FileWriter fw = new FileWriter("src/Carti.txt");
            BufferedWriter bw = new BufferedWriter(fw);

            for (List<Carte> listaCarti : memorie.getBiblioteca().values()) {
                for (Carte c : listaCarti) {
                    String linie = c.getTitlu() + "##" +
                            c.getAutor() + "##" +
                            c.getAn_publicare() + "##" +
                            c.isEsteDisponibila() + "##" +
                            c.getCategorie();

                    bw.write(linie);
                    bw.newLine();
                }
            }

            bw.close();
            fw.close();


        } catch (IOException e) {
            throw new RuntimeException("Eroareeeee " + e.getMessage(), e);
        }
    }

    /**
     * Generează un document text nou în dosarul dedicat rapoartelor, adăugând un marcaj temporal deasupra datelor statistice.
     *
     * @param titlu textul care va fi afișat ca antet în interiorul documentului
     * @param rezultat corpul principal de text conținând analiza extrasă
     * @param fileName denumirea fizică sub care va fi salvat documentul pe disc
     * @throws RuntimeException dacă dosarul destinație nu poate fi accesat sau creat
     */
    public void saveRaport(String titlu, String rezultat, String fileName)
    {
        try {
            FileWriter fw = new FileWriter("src/Rapoarte/" + fileName + ".txt", true);
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write("Generat la: " + LocalDateTime.now());
            bw.newLine();
            bw.write(titlu);
            bw.newLine();
            bw.write(rezultat);
            bw.newLine();

            bw.close();
            fw.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
