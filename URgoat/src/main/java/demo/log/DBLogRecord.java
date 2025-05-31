package demo.log;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Encja reprezentująca rekord logu w bazie danych.
 * Przechowuje informacje o wiadomości logu, czasie, poziomie, użytkowniku i operacji.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class DBLogRecord {

    /**
     * Unikalny identyfikator rekordu logu, generowany automatycznie.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Wiadomość logu.
     */
    private String message;

    /**
     * Sygnatura czasowa logu w formacie tekstowym.
     */
    private String timestamp;

    /**
     * Poziom logu (np. INFO, WARN, ERROR, DEBUG).
     */
    private String level;

    /**
     * Nazwa użytkownika powiązanego z operacją logu.
     */
    private String username;

    /**
     * Typ operacji logu, przechowywany jako wartość wyliczeniowa.
     */
    @Enumerated(EnumType.STRING)
    private LogOperacja operacja;
}