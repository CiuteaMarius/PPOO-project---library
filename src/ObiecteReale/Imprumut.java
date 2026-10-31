package ObiecteReale;

import java.time.LocalDateTime;

public class Imprumut {
    private String idCarte;
    private LocalDateTime data;
    private boolean esteActiv;
    private LocalDateTime dataRestituire;

    public Imprumut() {
        this.data = LocalDateTime.now();
        this.esteActiv = true;
        this.dataRestituire = null;
    }

    public Imprumut(String titlu, LocalDateTime data, boolean esteActiv, LocalDateTime dataRestituire) {
        this.idCarte = titlu;
        this.data = data;
        this.esteActiv = esteActiv;
        this.dataRestituire = dataRestituire;
    }

    public boolean isEsteActiv() {
        return esteActiv;
    }

    public void setEsteActiv(boolean esteActiv) {
        this.esteActiv = esteActiv;
    }

    public LocalDateTime getDataRestituire() {
        return dataRestituire;
    }

    public void setDataRestituire(LocalDateTime dataRestituire) {
        this.dataRestituire = dataRestituire;
    }

    public String getIdCarte() {
        return idCarte;
    }

    public void setIdCarte(String idCarte) {
        this.idCarte = idCarte;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }
}
