package demo.czat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WiadomoscDTO {
    private String tresc;
    private String nadawcaEmail;
    private String nadawcaDisplayName; // New field
    private int czatId;
    private String zdjecie;
    private int wiadomoscId; // New field
}
