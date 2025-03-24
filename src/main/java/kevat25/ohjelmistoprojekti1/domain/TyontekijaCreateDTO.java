package kevat25.ohjelmistoprojekti1.domain;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class TyontekijaCreateDTO {
    
    @NotEmpty(message = "Etunimi on pakollinen")
    private String etunimi;
    
    @NotEmpty(message = "Sukunimi on pakollinen")
    private String sukunimi;
    
    @Email(message = "Sähköpostiosoite ei ole kelvollinen")
    @NotEmpty(message = "Sähköposti on pakollinen")
    private String email;
    
    @NotEmpty(message = "Puhelinnumero on pakollinen")
    private String puhnro;
    
    @NotEmpty(message = "Katuosoite on pakollinen")
    private String katuosoite;
    
    @NotEmpty(message = "Salasana on pakollinen")
    @Size(min = 8, max = 60)
    private String bcrypthash;
    
    @NotNull(message = "Postinumero on pakollinen")
    private Postinumero postinumero;

    public TyontekijaCreateDTO() {
    }

    public TyontekijaCreateDTO(Postinumero postinumero, String katuosoite, String etunimi, String sukunimi,
                                      String email, String puhnro, String bcrypthash) {
        this.postinumero = postinumero;
        this.katuosoite = katuosoite;
        this.etunimi = etunimi;
        this.sukunimi = sukunimi;
        this.email = email;
        this.puhnro = puhnro;
        this.bcrypthash = bcrypthash;
    }

    public Postinumero getPostinumero() {
        return postinumero;
    }

    public void setPostinumero(Postinumero postinumero) {
        this.postinumero = postinumero;
    }

    public String getKatuosoite() {
        return katuosoite;
    }

    public void setKatuosoite(String katuosoite) {
        this.katuosoite = katuosoite;
    }

    public String getEtunimi() {
        return etunimi;
    }

    public void setEtunimi(String etunimi) {
        this.etunimi = etunimi;
    }

    public String getSukunimi() {
        return sukunimi;
    }

    public void setSukunimi(String sukunimi) {
        this.sukunimi = sukunimi;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPuhnro() {
        return puhnro;
    }

    public void setPuhnro(String puhnro) {
        this.puhnro = puhnro;
    }

    public String getBcrypthash() {
        return bcrypthash;
    }

    public void setBcrypthash(String bcrypthash) {
        this.bcrypthash = bcrypthash;
    }

}
