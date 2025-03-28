package kevat25.ohjelmistoprojekti1.domain;

public class LoginDTO {
    private String email;
    private String salasana;

    public LoginDTO() {}

    public LoginDTO(String email, String salasana) {
        this.email = email;
        this.salasana = salasana;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSalasana() {
        return salasana;
    }

    public void setSalasana(String salasana) {
        this.salasana = salasana;
    }
}
