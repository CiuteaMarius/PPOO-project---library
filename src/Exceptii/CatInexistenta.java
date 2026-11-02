package Exceptii;

/**
 * Excepție aruncată atunci când o categorie solicitată de utilizator nu se regăsește în baza de date a bibliotecii.
 */
public class CatInexistenta extends Exception{

    /**
     * Construiește o nouă excepție {@link CatInexistenta} furnizând denumirea categoriei negăsite.
     * @param msg textul explicativ care precizează numele categoriei care nu există în memorie
     */
    public CatInexistenta(String msg){
        super(msg);
    }
}
