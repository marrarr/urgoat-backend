package demo;

import demo.post.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Główna klasa aplikacji Spring Boot, odpowiedzialna za uruchomienie aplikacji.
 */
@SpringBootApplication
public class Application implements CommandLineRunner {

    /**
     * Punkt wejścia aplikacji, uruchamia aplikację Spring Boot.
     *
     * @param args Argumenty wiersza poleceń
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

//    @Autowired
//    private PostRepository postRepository;

    /**
     * Metoda wywoływana po uruchomieniu aplikacji, implementująca interfejs CommandLineRunner.
     *
     * @param args Argumenty wiersza poleceń
     */
    @Override
    public void run(String... args) {
    }
}