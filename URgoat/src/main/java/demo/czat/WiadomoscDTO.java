package demo.czat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Klasa DTO (Data Transfer Object) przechowująca dane wiadomości do transferu między warstwami aplikacji.
 * Zawiera informacje o treści wiadomości, nadawcy, czacie oraz zdjęciu.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WiadomoscDTO {

    /**
     * Treść wiadomości.
     */
    private String tresc;

    /**
     * Adres email nadawcy wiadomości.
     */
    private String nadawcaEmail;

    /**
     * Wyświetlana nazwa nadawcy wiadomości (np. pseudonim).
     */
    private String nadawcaDisplayName;

    /**
     * Identyfikator czatu, do którego należy wiadomość.
     */
    private int czatId;

    /**
     * Zdjęcie dołączone do wiadomości, zakodowane w formacie Base64.
     */
    private String zdjecie;

    /**
     * Unikalny identyfikator wiadomości.
     */
    private int wiadomoscId;
}