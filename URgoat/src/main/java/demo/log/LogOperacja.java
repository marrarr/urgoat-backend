package demo.log;

/**
 * Wyliczenie definiujące typy operacji rejestrowanych w logach systemu.
 * Używane do kategoryzacji działań użytkownika w systemie logowania.
 */
public enum LogOperacja {

    /**
     * Operacja dodawania nowego zasobu, np. posta, komentarza.
     */
    DODAWANIE,

    /**
     * Operacja usuwania zasobu, np. posta, komentarza.
     */
    USUWANIE,

    /**
     * Operacja aktualizacji istniejącego zasobu, np. treści komentarza.
     */
    AKTUALIZOWANIE,

    /**
     * Operacja logowania użytkownika do systemu.
     */
    LOGOWANIE,

    /**
     * Operacja wylogowania użytkownika z systemu.
     */
    WYLOGOWANIE
}