package kevat25.ohjelmistoprojekti1.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


//Työntekijän tiedot muokataan ilman salasanaa. Käytetään Patch-pyynnöissä.
@JsonInclude(Include.NON_NULL)
public class TyontekijaUpdateDTO {

    //Tyontekija -luokasta    
    private Long tyontekijaId;    
    private String etunimi;    
    private String sukunimi;    
    private String email;    
    private String puhnro;    
    private String katuosoite;

    //Postinumero -luokasta    
    private Postinumero postinumero;

    public TyontekijaUpdateDTO() {
    }

    public TyontekijaUpdateDTO(Long tyontekijaId, String etunimi, String sukunimi, String email, String puhnro,
            String katuosoite,
            Postinumero postinumero) {
        this.tyontekijaId = tyontekijaId;
        this.etunimi = etunimi;
        this.sukunimi = sukunimi;
        this.email = email;
        this.puhnro = puhnro;
        this.katuosoite = katuosoite;
        this.postinumero = postinumero;
    }

    public Long getTyontekijaId() {
        return tyontekijaId;
    }

    public void setTyontekijaId(Long tyontekijaId) {
        this.tyontekijaId = tyontekijaId;
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

    public String getKatuosoite() {
        return katuosoite;
    }

    public void setKatuosoite(String katuosoite) {
        this.katuosoite = katuosoite;
    }

    public Postinumero getPostinumero() {
        return postinumero;
    }

    public void setPostinumero(Postinumero postinumero) {
        this.postinumero = postinumero;
    }

}
