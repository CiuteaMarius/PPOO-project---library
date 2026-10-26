package ObiecteReale;

import java.time.LocalDateTime;

public class Imprumut {
    private String idCarte;
    private LocalDateTime data;


    public Imprumut() {
        this.data = LocalDateTime.now();
    }

    public Imprumut(String titlu, LocalDateTime data) {
        this.idCarte = titlu;
        this.data = data;
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
