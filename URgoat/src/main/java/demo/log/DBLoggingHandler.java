package demo.log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Handler;

/**
 * Komponent obsługujący zapisywanie logów do bazy danych.
 * Rozszerza klasę Handler z pakietu java.util.logging, umożliwiając przechowywanie rekordów logów w bazie danych.
 */
@Component
public class DBLoggingHandler extends Handler {

    private final DBLogRecordRepository logRecordRepository;

    /**
     * Konstruktor komponentu DBLoggingHandler, wstrzykujący repozytorium logów.
     *
     * @param logRecordRepository repozytorium do zapisywania rekordów logów
     */
    @Autowired
    public DBLoggingHandler(DBLogRecordRepository logRecordRepository) {
        this.logRecordRepository = logRecordRepository;
    }

    /**
     * Publikuje rekord logu do bazy danych, jeśli jest logowalny.
     * Tworzy nowy obiekt DBLogRecord, ustawia jego właściwości (wiadomość, czas, poziom, użytkownik, operacja)
     * i zapisuje go w bazie danych.
     *
     * @param record rekord logu z pakietu java.util.logging
     */
    @Override
    public void publish(java.util.logging.LogRecord record) {
        if (!isLoggable(record)) {
            return;
        }

        String locLevel = record.getLevel().getName();

        DBLogRecord logRecord = new DBLogRecord();
        logRecord.setMessage(record.getMessage());

        LocalDateTime localData = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = localData.format(formatter);
        logRecord.setTimestamp(formattedDateTime);
        logRecord.setLevel(locLevel);

        Object[] objekty = record.getParameters();
        logRecord.setUsername(objekty[0].toString());
        logRecord.setOperacja((LogOperacja) objekty[1]);

        logRecordRepository.save(logRecord);
    }

    /**
     * Pusta implementacja metody flush.
     * Nie wykonuje żadnych operacji, ponieważ zapisywanie do bazy danych jest natychmiastowe.
     */
    @Override
    public void flush() {}

    /**
     * Pusta implementacja metody close.
     * Nie wykonuje żadnych operacji, ponieważ nie ma zasobów do zwolnienia.
     *
     * @throws SecurityException jeśli wystąpi problem z uprawnieniami
     */
    @Override
    public void close() throws SecurityException {}
}