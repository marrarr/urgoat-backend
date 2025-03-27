package demo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UzytkownikRepository extends JpaRepository<Uzytkownik, Long>{
    //Wybrane imiona
    List<Uzytkownik> findByImie(String imie);

    //Wybrane nazwiska
    List<Uzytkownik> findByNazwisko(String nazwisko);

    //Wybranie osob o ustalonym imieniu i nazwisku
    List<Uzytkownik> findByImieAndNazwisko(String imie,String nazwisko);

    //Wybranie po pseudonimie
    List<Uzytkownik> findByPseudonim(String pseudonim);
}
