package ObiecteReale;

import java.time.LocalDateTime;
/**
 * Clasa care gestionează detaliile unei tranzacții de împrumut.
 * Face legătura între identificatorul unic al unei cărți și momentele calendaristice de preluare și restituire.
 */
public class Imprumut {
    private String idCarte;
    private LocalDateTime data;
    private boolean esteActiv;
    private LocalDateTime dataRestituire;

    /**
     * Construiește un împrumut nou, înregistrând automat data și ora curente ca moment de preluare,
     * setând starea tranzacției ca fiind activă.
     */
    public Imprumut() {
        this.data = LocalDateTime.now();
        this.esteActiv = true;
        this.dataRestituire = null;
    }


    /**
     * Construiește un împrumut folosind date istorice extrase din memoria persistentă.
     *
     * @param titlu Identificatorul unic al cărții, format prin concatenarea atributelor sale.
     * @param data Data și ora la care a fost efectuat împrumutul.
     * @param esteActiv Indicator logic care precizează dacă tranzacția este încă în curs.
     * @param dataRestituire Data și ora la care cartea a fost înapoiată, sau valoarea nulă dacă nu a fost returnată.
     */
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
