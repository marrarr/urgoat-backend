package demo.log;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Klasa narzędziowa do logowania zdarzeń w aplikacji.
 * Zapewnia metody do rejestrowania komunikatów na różnych poziomach logowania (INFO, WARNING, ERROR)
 * oraz specjalistyczne metody do logowania działań użytkownika.
 */
public class URgoatLogger {

    private static final Logger logger = Logger.getLogger("URgoatLogger");

    /**
     * Loguje komunikat na poziomie INFO.
     *
     * @param message treść komunikatu do zalogowania
     */
    public static void info(String message) {
        logger.log(Level.INFO, message);
    }

    /**
     * Loguje komunikat na poziomie WARNING.
     *
     * @param message treść komunikatu do zalogowania
     */
    public static void warning(String message) {
        logger.log(Level.WARNING, message);
    }

    /**
     * Loguje komunikat na poziomie ERROR.
     *
     * @param message treść komunikatu do zalogowania
     */
    public static void error(String message) {
        logger.log(Level.SEVERE, message);
    }

    /**
     * Loguje informacje o działaniu użytkownika na poziomie INFO.
     * Rejestruje komunikat wraz z nazwą użytkownika i typem operacji.
     *
     * @param message treść komunikatu do zalogowania
     * @param username nazwa użytkownika wykonującego operację
     * @param operacja typ operacji (np. DODAWANIE, USUWANIE)
     */
    public static void uzytkownikInfo(String message, String username, LogOperacja operacja) {
        logger.log(Level.INFO, message, new Object[]{username, operacja});
    }

    /* public static void userAction(String message, String username,
                                  String objectType, String actionType) {
        userAction(message, username, objectType, actionType, null);
    }

    public static void error(String message, String username,
                             String objectType, String actionType, Long objectId) {
        logger.log(Level.SEVERE, message, new Object[]{username, objectType, actionType, objectId});
    } */
}