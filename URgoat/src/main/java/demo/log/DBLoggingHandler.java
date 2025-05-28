package demo.log;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Handler;

@Component
public class DBLoggingHandler extends Handler {

    private final DBLogRecordRepository logRecordRepository;

    @Autowired
    public DBLoggingHandler(DBLogRecordRepository logRecordRepository) {
        this.logRecordRepository = logRecordRepository;
    }

    @Override
    public void publish(java.util.logging.LogRecord record) {

        if (!isLoggable(record)) {
            return;
        }

        String locLevel = record.getLevel().getName();

        DBLogRecord logRecord = new DBLogRecord();
        logRecord.setMessage(record.getMessage());

        LocalDateTime localData = LocalDateTime.now(); // Utworzenie formatera dla czasu
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = localData.format(formatter); // Formatowanie czasu
        logRecord.setTimestamp(formattedDateTime);
        logRecord.setLevel(locLevel);

        Object[] objekty = record.getParameters();
        logRecord.setUsername(objekty[0].toString());
        logRecord.setOperacja((LogOperacja)objekty[1]);

        logRecordRepository.save(logRecord);
    }

    @Override
    public void flush() {}

    @Override
    public void close() throws SecurityException {}
}