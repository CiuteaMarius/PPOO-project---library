package Actiuni;

import ObiecteReale.Carte;
import ObiecteReale.Imprumut;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public class SalveazaInFisiere {
    private ManagerMemorie memorie;

    public SalveazaInFisiere(ManagerMemorie memorie) {
        this.memorie = memorie;

    }

    public void salveazaTOT()
    {
        salveazaUtilizatorii();
        salveazaImprumuturi();
        salveazaCartile();
    }

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
