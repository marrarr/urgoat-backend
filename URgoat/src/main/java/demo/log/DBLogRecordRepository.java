package demo.log;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface DBLogRecordRepository extends JpaRepository<DBLogRecord, Long>, JpaSpecificationExecutor<DBLogRecord> {

    List<DBLogRecord> findAll(Specification<DBLogRecord> spec, Sort timestamp);

}
