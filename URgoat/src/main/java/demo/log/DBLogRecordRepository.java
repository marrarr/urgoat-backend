package demo.log;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DBLogRecordRepository extends JpaRepository<DBLogRecord, Long> {
}
