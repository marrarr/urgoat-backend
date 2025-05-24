package demo.log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.logging.Level;
import java.util.logging.Logger;

@Configuration
public class LoggingConfig {

    @Autowired
    private DBLoggingHandler databaseLoggingHandler;

    @Bean
    public Logger configureLogger() {
        Logger logger = Logger.getLogger("MyAppLogger");
        logger.setLevel(Level.INFO);  // Ustawiamy poziom logowania

        // Dodajemy niestandardowy handler do logowania
        logger.addHandler(databaseLoggingHandler);

        // Opcjonalnie: dodanie dodatkowego handlera, np. do konsoli
        //ConsoleHandler consoleHandler = new ConsoleHandler();
        //logger.addHandler(consoleHandler);

        return logger;
    }
}
