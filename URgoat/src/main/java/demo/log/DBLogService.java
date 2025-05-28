package demo.log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DBLogService {
    @Autowired
    private DBLogRecordRepository dbLogRecordRepository;

    public List<DBLogRecord> findAll() {
        return dbLogRecordRepository.findAll();
    }

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
