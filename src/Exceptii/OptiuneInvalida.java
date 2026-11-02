package Exceptii;

/**
 * Excepție verificată (checked) aruncată în momentul în care utilizatorul introduce de la consolă o opțiune numerică ce se află în afara limitelor meniului curent.
 * Se declanșează, de exemplu, dacă la autentificare se introduce o altă valoare decât 1 sau 2, respectiv dacă selecția numerică dintr-un submeniu nu corespunde niciunei opțiuni valide.
 */
public class OptiuneInvalida extends Exception{

    /**
     * Construiește o nouă excepție {@link OptiuneInvalida} specificând opțiunea incorectă sau opțiunile permise.
     *
     * @param mesaj textul explicativ afișat utilizatorului pentru a reîncerca selecția
     */
    public OptiuneInvalida(String mesaj) {
        super(mesaj);
    }
}
