package Actiuni;

import Exceptii.AnInvalid;
import Exceptii.DateInvalide;
import ObiecteReale.Carte;
import ObiecteReale.Imprumut;
import ObiecteReale.Utilizator;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.*;

/**
 * Gestionează stocarea temporară a datelor pe parcursul execuției programului.
 * Colecțiile din această clasă reprezintă baza de date în memoria RAM, fiind populate la pornirea aplicației.
 */
public class ManagerMemorie {

    private Set<String> categorii = new HashSet<>();

    private Map<String, List<Carte>> biblioteca = new HashMap<>();
    private Set<String> utilizatori = new HashSet<>();
    private Map<String, List<Imprumut>> imprumuturi = new HashMap<>();


    /**
     * Încarcă datele despre cărți din fișierul text corespunzător și le populează în colecțiile interne.
     * Procesul utilizează un mecanism defensiv care captează intern {@link DateInvalide}, {@link AnInvalid} și {@link NumberFormatException} pentru a ignora liniile corupte
     * din fișier, permițând continuarea încărcării pentru restul datelor valide.
     *
     * @throws RuntimeException dacă fișierul sursă nu este găsit sau dacă apar erori la citirea fluxului de date
     */
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
                    try {
                        String[] bucati = linie.split("##");

                        if (bucati.length != 5) {
                            throw new DateInvalide("Linie incompletă sau coruptă.");
                        }
                        if (bucati[0].isEmpty() || bucati[1].isEmpty() || bucati[4].isEmpty()) {
                            throw new DateInvalide("Text lipsă pentru titlu, autor sau categorie.");
                        }

                        int an = Integer.parseInt(bucati[2]);
                        int anCurent = LocalDate.now().getYear();

                        if (an < 0 || an > anCurent) {
                            throw new AnInvalid("An publicare invalid: " + an);
                        }

                        Carte c = new Carte(bucati[0], bucati[1], an, Boolean.parseBoolean(bucati[3]), bucati[4]);
                        categorii.add(c.getCategorie());

                        if(biblioteca.containsKey(c.getCategorie())) {
                            biblioteca.get(c.getCategorie()).add(c);
                        } else {
                            biblioteca.put(c.getCategorie(), new ArrayList<>());
                            biblioteca.get(c.getCategorie()).add(c);
                        }
                    } catch (DateInvalide | AnInvalid | NumberFormatException e) {
                        System.out.println("Eroare la încărcarea unei cărți din fișier: " + e.getMessage() + ". Linia a fost ignorată.");
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


    /**
     * Extrage istoricul tranzacțiilor din fișierul text dedicat împrumuturilor și reconstruiește obiectele asociate.
     * Orice anomalie legată de informații lipsă sau de parsarea incorectă a datei calendaristice este captează intern prin {@link DateInvalide} și {@link DateTimeParseException}, linia problematică fiind ignorată.
     * @throws RuntimeException dacă apar probleme fizice la citirea fișierului de pe disc
     */
    public void readBorrowed()
    {
        String path = "src/Împrumuturi.txt";
        try {
            FileReader fr = new FileReader(path);
            BufferedReader bfr = new BufferedReader(fr);

            String linie = bfr.readLine();

            while(linie != null){

                try {
                    String[] bucati = linie.split("##");

                    if (bucati.length != 5) {
                        throw new DateInvalide("Linie incompletă sau coruptă în fișierul de împrumuturi.");
                    }
                    if (bucati[0].isEmpty() || bucati[1].isEmpty()) {
                        throw new DateInvalide("Nume utilizator sau ID carte lipsă.");
                    }

                    String nume = bucati[0];
                    String idCarte = bucati[1];
                    LocalDateTime data = LocalDateTime.parse(bucati[2]);
                    boolean esteActiv = Boolean.parseBoolean(bucati[3]);
                    LocalDateTime dataRest = null;

                    if (!bucati[4].equals("null")) {
                        dataRest = LocalDateTime.parse(bucati[4]);
                    }

                    Imprumut imprumut = new Imprumut(idCarte, data, esteActiv, dataRest);

                    if(imprumuturi.containsKey(nume)) {
                        imprumuturi.get(nume).add(imprumut);
                    } else {
                        imprumuturi.put(nume, new ArrayList<>());
                        imprumuturi.get(nume).add(imprumut);
                    }
                } catch (DateInvalide | DateTimeParseException e) {
                    System.out.println("Eroare la încărcarea unui împrumut din fișier: " + e.getMessage() + ". Linia a fost ignorată.");
                }

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


    /**
     * Populează mulțimea utilizatorilor citind numele acestora dintr-un fișier text simplu.
     *
     * @throws RuntimeException dacă fișierul nu poate fi accesat sau citit de către sistemul de operare
     */
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

    /**
     * Asociază o nouă tranzacție profilului unui utilizator.
     * @param numeUtilizator textul care reprezintă cheia de identificare a clientului
     * @param imprumut obiectul care detaliază cartea și momentul tranzacției
     */
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
