package demo.log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Klasa konfiguracyjna dla systemu logowania.
 * Definiuje i konfiguruje logger aplikacji, dodając niestandardowy handler do zapisywania logów w bazie danych.
 */
@Configuration
public class LoggingConfig {

    @Autowired
    private DBLoggingHandler databaseLoggingHandler;

    /**
     * Konfiguruje i zwraca logger dla aplikacji.
     * Ustawia poziom logowania na INFO i dodaje handler do zapisywania logów w bazie danych.
     *
     * @return skonfigurowany obiekt Logger
     */
    @Bean
    public Logger configureLogger() {
        Logger logger = Logger.getLogger("URgoatLogger");
        logger.setLevel(Level.INFO);
        logger.addHandler(databaseLoggingHandler);

        return logger;
    }
}