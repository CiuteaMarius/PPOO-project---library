package ObiecteReale;


import java.util.ArrayList;
import java.util.List;

public class Utilizator {
    private boolean esteBibliotecar;
    private String username;
    private List<Carte> cartiImprumutate;

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
    public void adaugaCarteImprumutata(Carte carte)
    {

    }
}
