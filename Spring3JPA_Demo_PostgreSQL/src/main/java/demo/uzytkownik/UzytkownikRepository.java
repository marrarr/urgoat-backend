package demo.uzytkownik;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UzytkownikRepository extends JpaRepository<Uzytkownik, Long>{
    //Wyszukaj użytkownika po imieniu
    List<Uzytkownik> findByImie(String imie);

    //Wyszukaj użytkownika po nazwisku
    List<Uzytkownik> findByNazwisko(String nazwisko);

    //Wyszukaj użytkownika po imieniu i nazwisku
    List<Uzytkownik> findByImieAndNazwisko(String imie,String nazwisko);

    //Wyszukiwanie po pseudonimie
    List<Uzytkownik> findByPseudonim(String pseudonim);
}
