package demo.service;
import demo.model.*;
import demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataLoader implements ApplicationRunner{

    private UzytkownikRepository uzytkownikRepository;
    private CzatRepository czatRepository;
    private KomentarzRepository komentarzRepository;
    private PostRepository postRepository ;
    private ReakcjaRepository reakcjaRepository;
    private WiadomoscRepository wiadomoscRepository;
    private ZnajomyRepository znajomyRepository;

    @Autowired
    public DataLoader(UzytkownikRepository uzytkownikRepository,
                      CzatRepository czatRepository,
                      KomentarzRepository komentarzRepository,
                      PostRepository postRepository,
                      ReakcjaRepository reakcjaRepository,
                      WiadomoscRepository wiadomoscRepository,
                      ZnajomyRepository znajomyRepository) {
        this.uzytkownikRepository = uzytkownikRepository;
        this.czatRepository = czatRepository;
        this.komentarzRepository = komentarzRepository;
        this.postRepository = postRepository;
        this.reakcjaRepository = reakcjaRepository;
        this.wiadomoscRepository = wiadomoscRepository;
        this.znajomyRepository = znajomyRepository;
    }

    public void run(ApplicationArguments args) {

        if (uzytkownikRepository.count() == 0) //Przykladowe dane dodajemy tylko jak tabela jest pusta
        {
            //użytkownicy
            Uzytkownik user1 = new Uzytkownik("Adam","Nawrocki","Ados","adamnawrocki@gmail.com",1);
            Uzytkownik user2 = new Uzytkownik("Natalia","Kowalska","Natik","natkowalska@gmail.com",1);
            Uzytkownik user3 = new Uzytkownik("Bartosz","Krawczyk","baczyk","bartoszkrawczyk@gmail.com",1);
            Uzytkownik user4 = new Uzytkownik("Damian","Ostry","ostyga","damianostry@gmail.com",1);

            uzytkownikRepository.save(user1);
            uzytkownikRepository.save(user2);
            uzytkownikRepository.save(user3);
            uzytkownikRepository.save(user4);

            //znajomi
            Znajomy znajomy1 = new Znajomy(user1, user2);

            znajomyRepository.save(znajomy1);

            //czat
            Czat czat1 = new Czat(List.of(user1, user2));

            czatRepository.save(czat1);

            //wiadomości czatowe
            Wiadomosc wiadomosc1 = new Wiadomosc(czat1, "Cześć, co tam?");
            Wiadomosc wiadomosc2 = new Wiadomosc(czat1, "Dobrze a co u cb?");

            wiadomoscRepository.save(wiadomosc1);
            wiadomoscRepository.save(wiadomosc2);

            //Posty
            Post post1 = new Post(user1,"Pierwszy post testowy!");
            Post post2 = new Post(user3,"Drugi post testowy!");

            postRepository.save(post1);
            postRepository.save(post2);

            //Komentarze
            Komentarz komentarz1 = new Komentarz(post1,user2,"Czy test pierwszy przebiegl pomyslnie");
            Komentarz komentarz2 = new Komentarz(post2,user4,"Za duzo tych testow");

            komentarzRepository.save(komentarz1);
            komentarzRepository.save(komentarz2);
        }
    }
}
