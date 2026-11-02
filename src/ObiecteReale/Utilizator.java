package ObiecteReale;


import java.util.ArrayList;
import java.util.List;

/**
 * Clasa care definește un actor din cadrul sistemului.
 * Poate reprezenta atât un client obișnuit care împrumută lucrări, cât și un administrator de tip bibliotecar.
 */
public class Utilizator {
    private boolean esteBibliotecar;
    private String username;
    private List<Carte> cartiImprumutate;

    /**
     * Construiește un profil de utilizator nou, inițializând numele cu o valoare predefinită
     * și pregătind o listă goală pentru viitoarele cărți împrumutate.
     */
    public Utilizator() {
        username = "default";
        this.cartiImprumutate = new ArrayList<>();
    }

    public boolean getEsteBibliotecar() {
        return esteBibliotecar;
    }

    public void setEsteBibliotecar(boolean esteBibliotecar) {
        this.esteBibliotecar = esteBibliotecar;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Carte> getCartiImprumutate() {
        return cartiImprumutate;
    }

    public void setCartiImprumutate(List<Carte> cartiImprumutate) {
        this.cartiImprumutate = cartiImprumutate;
    }

}
