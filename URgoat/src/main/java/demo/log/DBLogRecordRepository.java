package demo.log;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * Repozytorium JPA dla encji DBLogRecord.
 * Umożliwia wykonywanie zapytań na rekordach logów, w tym filtrowanie i sortowanie.
 */
public interface DBLogRecordRepository extends JpaRepository<DBLogRecord, Long>, JpaSpecificationExecutor<DBLogRecord> {

    /**
     * Wyszukuje rekordy logów zgodnie z podaną specyfikacją i porządkiem sortowania.
     *
     * @param spec specyfikacja określająca warunki filtrowania rekordów
     * @param timestamp porządek sortowania, zazwyczaj według pola timestamp
     * @return lista rekordów logów spełniających specyfikację, posortowana według podanego porządku
     */
    List<DBLogRecord> findAll(Specification<DBLogRecord> spec, Sort timestamp);
}