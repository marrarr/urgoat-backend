package demo.log;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class DBLogRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String message;
    private String timestamp;
    private String level;
    private String username;

    @Enumerated(EnumType.STRING)
    private LogOperacja operacja;

    public DBLogRecord() {

    }

    public DBLogRecord(String message, String timestamp) {
        this.message = message;
        this.timestamp = timestamp;
        this.level = "INFO";
    }
}