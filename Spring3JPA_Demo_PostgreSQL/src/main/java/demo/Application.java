package demo;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.nio.file.Files;
import java.nio.file.Path;

@SpringBootApplication
public class Application implements CommandLineRunner {

    public static void main(String[] args) {

        SpringApplication.run(Application.class, args);
    }
    

    @Autowired
    private PostRepository postRepository;


    @Override
    public void run(String... args) {
              
        //Wstawienie osob do bazy
        //uzytkownikRepository.save(new Uzytkownik(1,"Anita","Uto", zmienna zdjęcia ,"AniU","anita@gmail.com",1));

        
        //----------------------------------------------------
        

       
        //---------------------------------------------------
        
        
        

    }
}

