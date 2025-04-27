package demo;

import demo.post.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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

