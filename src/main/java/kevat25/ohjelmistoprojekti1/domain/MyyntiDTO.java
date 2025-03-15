package kevat25.ohjelmistoprojekti1.domain;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public class MyyntiDTO {

    // Myynti-entitystä
    @NotNull (message= "Myynnin aika tulee merkitä")
    private LocalDateTime myyntiaika;

    @NotNull (message = "Työntekijän tunnus tulee olla mukana")
    private Long tyontekijaId;

    @Email (message = "Sähköpostin tulee olla oikeassa muodossa")
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

    public List<LippuDTO> getLiput() {
        return liput;
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

public void setLiput(List<LippuDTO> liput) {
    this.liput = liput;
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