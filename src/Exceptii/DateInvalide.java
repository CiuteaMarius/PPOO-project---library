package Exceptii;

/**
 * Excepție utilizată pentru semnalarea erorilor de validare a datelor de intrare.
 * <p>
 * Apare atunci când un câmp text obligatoriu este transmis ca fiind gol sau conține doar spații,
 * când un text nu poate fi parsat conform formatului calendaristic {@code AAAA-LL-ZZ},
 * sau când o linie extrasă dintr-un fișier text este incompletă ori coruptă.
 */
public class DateInvalide extends RuntimeException {

    /**
     * Construiește o nouă excepție {@link DateInvalide} cu un mesaj descriptiv al regulii încălcate.
     *
     * @param message descrierea detaliată a erorii de validare întâmpinate
     */
    public DateInvalide(String message) {
        super(message);
    }
}
