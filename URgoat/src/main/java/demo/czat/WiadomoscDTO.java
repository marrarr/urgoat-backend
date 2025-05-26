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
    private int czatId;
    private byte[] zdjecie;
}
