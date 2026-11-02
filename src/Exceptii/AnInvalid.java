package Exceptii;

/**
 * Excepție unchecked aruncată în momentul în care un an calendaristic introdus de la tastatură sau citit din fișiere este invalid din punct de vedere logic.
 * Se declanșează dacă valoarea numerică furnizată este negativă sau dacă depășește anul curent.
 */
public class AnInvalid extends RuntimeException {
    public AnInvalid(String message) {
        super(message);
    }
}
