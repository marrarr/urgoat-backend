package demo.log;

import java.util.logging.Level;
import java.util.logging.Logger;

public class URgoatLogger {
    private static final Logger logger = Logger.getLogger("URgoatLogger");

    // zwykle metody do logowania gdyby była potrzeba
    public static void info(String message) {
        logger.log(Level.INFO, message);
    }

    public static void warning(String message) {
        logger.log(Level.WARNING, message);
    }

    public static void error(String message) {
        logger.log(Level.SEVERE, message);
    }

    // metody do wygodnego dodawania logow w kodzie
    public static void uzytkownikInfo(String message, String username, LogOperacja operacja) {
        logger.log(Level.INFO, message, new Object[]{username, operacja});
    }

 /*   public static void userAction(String message, String username,
                                  String objectType, String actionType) {
        userAction(message, username, objectType, actionType, null);
    }

    public static void error(String message, String username,
                             String objectType, String actionType, Long objectId) {
        logger.log(Level.SEVERE, message, new Object[]{username, objectType, actionType, objectId});
    }*/
}
