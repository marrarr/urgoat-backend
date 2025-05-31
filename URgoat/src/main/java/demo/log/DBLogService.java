package demo.log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Serwis obsługujący operacje związane z rekordami logów w bazie danych.
 * Umożliwia wyszukiwanie logów z opcjonalnym filtrowaniem według nazwy użytkownika, operacji i poziomu logu.
 */
@Service
public class DBLogService {

    @Autowired
    private DBLogRecordRepository dbLogRecordRepository;

    /**
     * Wyszukuje rekordy logów z bazy danych z opcjonalnym filtrowaniem.
     * Wyniki są sortowane malejąco według znacznika czasu.
     *
     * @param username nazwa użytkownika do filtrowania (może być null lub pusty)
     * @param operacja typ operacji do filtrowania (może być null)
     * @param level poziom logu do filtrowania (np. INFO, WARN, ERROR, DEBUG; może być null lub pusty)
     * @return lista rekordów logów spełniających podane kryteria filtrowania
     */
    public List<DBLogRecord> findAllFiltruj(String username, LogOperacja operacja, String level) {
        Specification<DBLogRecord> spec = Specification.where(null);

        if (username != null && !username.isEmpty()) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("username"), username));
        }

        if (operacja != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("operacja"), operacja));
        }

        if (level != null && !level.isEmpty()) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("level"), level));
        }

        return dbLogRecordRepository.findAll(spec, Sort.by(Sort.Direction.DESC, "timestamp"));
    }
}