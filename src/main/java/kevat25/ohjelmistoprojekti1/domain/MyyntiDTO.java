package kevat25.ohjelmistoprojekti1.domain;

import java.time.LocalDateTime;
import java.util.List;

public class MyyntiDTO {

    // Myynti-entitystä
    private LocalDateTime myyntiaika;
    private Long tyontekijaId;
    private String email;
    private Long myyntiId;

    // LippuDTO:t lisätään tänne listana
    private List<LippuDTO> liput;

    // Getterit
    public LocalDateTime getMyyntiaika() {
        return myyntiaika;
    }

    public Long getTyontekijaId() {
        return tyontekijaId;
    }

    public String getEmail() {
        return email;
    }

    public Long getMyyntiId() {
    return myyntiId;
    }



    // Setterit
    public void setMyyntiaika(LocalDateTime myyntiaika) {
        this.myyntiaika = myyntiaika;
    }

    public void setTyontekijaId(Long tyontekijaId) {
        this.tyontekijaId = tyontekijaId;
    }

    public void setEmail(String email) {
        this.email = email;
    }

public void setMyyntiId(Long myyntiId) {
    this.myyntiId = myyntiId;
}


    // Parametriton konstruktori:
    public MyyntiDTO() {

    }

    // Konstruktori:
    public MyyntiDTO(LocalDateTime myyntiaika, Long tyontekijaId,
            String email, Long myyntiId, List<LippuDTO> liput) {
        this.myyntiaika = myyntiaika;
        this.tyontekijaId = tyontekijaId;
        this.email = email;
        this.myyntiId = myyntiId;
        this.liput = liput;
    }
}