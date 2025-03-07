package kevat25.ohjelmistoprojekti1.domain;

public class MyyntiDTO {
    private String email;
    private Long tyontekijaId;

    public MyyntiDTO() {
    }

    public MyyntiDTO(String email, Long tyontekijaId) {
        this.email = email;
        this.tyontekijaId = tyontekijaId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getTyontekijaId() {
        return tyontekijaId;
    }

    public void setTyontekijaId(Long tyontekijaId) {
        this.tyontekijaId = tyontekijaId;
    }
}
