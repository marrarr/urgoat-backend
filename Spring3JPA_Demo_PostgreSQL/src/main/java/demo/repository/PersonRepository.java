package demo.repository;

import java.util.List;

import demo.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PersonRepository extends JpaRepository<Person, Long> {

//Wybrane nazwiska    
List<Person> findByLastName(String lastName);

//Wybranie osob o ustalonym imieniu i nazwisku
List<Person> findByFirstNameAndLastName(String firstName,String lastName);

//Osoby mlodsze niz podany parametr (posortowane malejaco wedlug wieku)
List<Person> findByAgeLessThanOrderByAgeDesc(int age);

//Osoby starsze niz podany parametr (posortowane rosnaco wedlug wieku)
List<Person> findByAgeGreaterThanOrderByAgeAsc(int age);

//Osoby ciezsze niz podany parametr
List<Person> findByWeightGreaterThan(double weight);

//Osoby lzejsze niz podany parametr
List<Person> findByWeightLessThan(double weight);

//Zliczenie liczby imion osob o podanym imieniu
long countByLastName(String lastName);

//Wyszukiwanie w oparciu o zapytanie SQL (osoby starsze od progu i majace koncowke nazwiska: ski)
@Query("FROM Person p WHERE p.age> ?1 AND p.lastName LIKE '%ski'") 
List<Person> findBySQLQuery(int age);


//LISTA OPERATOROW DO TWORZENIA ZAPYTAN
/*
IsAfter, After, IsGreaterThan, GreaterThan;
IsGreaterThanEqual, GreaterThanEqual;
IsBefore, Before, IsLessThan, LessThan;
IsLessThanEqual, LessThanEqual;
IsBetween, Between;
IsNull, Null;
IsNotNull, NotNull;
IsIn, In;
IsNotIn, NotIn;
IsStartingWith, StartingWith, StartsWith;
IsEndingWith, EndingWith, EndsWith;
IsContaining, Containing, Contains;
IsLike, Like;
IsNotLike, NotLike;
IsTrue, True;
IsFalse, False;
Is, Equals;
IsNot, Not;
And, Or
OrderBy
*/

}
