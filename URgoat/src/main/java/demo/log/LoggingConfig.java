package demo.log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

@Configuration
public class LoggingConfig {

    @Autowired
    private DBLoggingHandler databaseLoggingHandler;

    @Bean
    public Logger configureLogger() {
        Logger logger = Logger.getLogger("URgoatLogger");
        logger.setLevel(Level.INFO);
        logger.addHandler(databaseLoggingHandler);

        return logger;
    }
}
