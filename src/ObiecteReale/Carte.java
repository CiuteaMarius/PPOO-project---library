package ObiecteReale;

public class Carte {


    private String titlu;
    private String autor;
    private int an_publicare;
    private boolean esteDisponibila;
    private String categorie;


    public Carte() {

        this.titlu = "";
        this.autor = "";
        this.an_publicare = 0;
        this.esteDisponibila = false;
        this.categorie = "";


    }

    public Carte(String titlu, String autor, int an_publicare, boolean esteDisponibila, String categrorie) {
        this.titlu = titlu;
        this.autor = autor;
        this.an_publicare = an_publicare;
        this.esteDisponibila = esteDisponibila;
        this.categorie = categrorie;


    }



    public String getTitlu() {
        return titlu;
    }

    public void setTitlu(String titlu) {
        this.titlu = titlu;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public int getAn_publicare() {
        return an_publicare;
    }

    public void setAn_publicare(int an_publicare) {
        this.an_publicare = an_publicare;
    }

    public boolean isEsteDisponibila() {
        return esteDisponibila;
    }

    public void setEsteDisponibila(boolean esteDisponibila) {
        this.esteDisponibila = esteDisponibila;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    @Override
    public String toString() {
        String first =  "\"" + this.titlu + "\"" + ", scris de " +
                this.autor + "                  publicată în anul " + this.an_publicare;
        String second = "";
        if(this.esteDisponibila == true)
        {
            second = " ---- Cartea este disponibilă în bibliotecă.";
        }
        else{
            second = " ---- Cartea există în baza de date a bibliotecii, dar nu și fizic. Ea a fost împrumutată unui client";
        }
        return first + second;
    }

    public String toStringLite()
    {
        return this.titlu + " scrisă de " + this.autor + " în anul" + this.an_publicare;
    }
    public String toStringLite2(){return this.titlu + " publicată în anul " + this.an_publicare;}
}
