package kevat25.ohjelmistoprojekti1.domain;

import jakarta.validation.constraints.NotEmpty;

public class LoginDTO {
    @NotEmpty(message = "Sähköposti on pakollinen")
    private String email;
    @NotEmpty(message = "Salasana on pakollinen")
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
