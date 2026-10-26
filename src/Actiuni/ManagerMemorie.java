package Actiuni;

import ObiecteReale.Carte;
import ObiecteReale.Imprumut;
import ObiecteReale.Utilizator;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

public class ManagerMemorie {

    private Set<String> categorii = new HashSet<>();

    private Map<String, List<Carte>> biblioteca = new HashMap<>();
    private Set<String> utilizatori = new HashSet<>();
    private Map<String, List<Imprumut>> imprumuturi = new HashMap<>();


    public void citesteTXT()
    {
        String path = "src/Carti.txt";

        try {
            FileReader fr = new FileReader(path);
            BufferedReader bfr = new BufferedReader(fr);

            String linie = bfr.readLine();

            if(linie == null)
            {
                System.out.println("\nBiblioteca este complet goala.\n");
            }else{
                while(linie != null)
                {
                    String[] bucăți = linie.split("##");  //vine: [0] Titlu, [1]autorul. [2] anul, [3] disponibilitate, [4]categorie
                    Carte c = new Carte(bucăți[0], bucăți[1], Integer.parseInt(bucăți[2]), Boolean.parseBoolean(bucăți[3]), bucăți[4]);

                    categorii.add(c.getCategorie());

                    if(biblioteca.containsKey(c.getCategorie()))
                    {
                        biblioteca.get(c.getCategorie()).add(c);

                    }else{
                        biblioteca.put(c.getCategorie(), new ArrayList<>());
                        biblioteca.get(c.getCategorie()).add(c);

                    }

                    linie = bfr.readLine();

                }
            }


        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public void readBorrowed()
    {
        String path = "src/Împrumuturi.txt";
        try {
            FileReader fr = new FileReader(path);
            BufferedReader bfr = new BufferedReader(fr);

            String linie = bfr.readLine();

            while(linie != null){

                String[] bucăți = linie.split("##");
                String nume = bucăți[0];
                String idCarte = bucăți[1];



                String dataText = bucăți[2];
                LocalDateTime data = LocalDateTime.parse(dataText);
                Imprumut imprumut = new Imprumut(idCarte, data);

                if(imprumuturi.containsKey(nume))
                {

                    imprumuturi.get(nume).add(imprumut);

                }else{

                    imprumuturi.put(nume, new ArrayList<>());
                    imprumuturi.get(nume).add(imprumut);
                }
//                String[] bucati2 = idCarte.split("!#!");
//                String titlu = bucati2[0];
//                String autor = bucati2[1];
//                String an = bucati2[2];

                linie = bfr.readLine();
            }
            bfr.close();
            fr.close();

        }catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void readUsers()
    {
        String path = "src/Utilizatori.txt";
        try {
            FileReader fr = new FileReader(path);
            BufferedReader bfr = new BufferedReader(fr);

            String linie = bfr.readLine();

            while(linie != null){
                utilizatori.add(linie);
                linie = bfr.readLine();
            }

        }catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public void adaugaUtilizator(Utilizator utilizator)
    {
        utilizatori.add(utilizator.getUsername());
    }

    public void adaugaImprumut(String numeUtilizator, Imprumut imprumut)
    {
        if(imprumuturi.containsKey(numeUtilizator))
        {
            imprumuturi.get(numeUtilizator).add(imprumut);
        }else{
            imprumuturi.put(numeUtilizator, new ArrayList<>());
            imprumuturi.get(numeUtilizator).add(imprumut);

        }
    }



    public Set<String> getCategorii() {
        return categorii;
    }

    public Map<String, List<Carte>> getBiblioteca() {
        return biblioteca;
    }

    public Set<String> getUtilizatori() {
        return utilizatori;
    }

    public Map<String, List<Imprumut>> getImprumuturi() {
        return imprumuturi;
    }
}
