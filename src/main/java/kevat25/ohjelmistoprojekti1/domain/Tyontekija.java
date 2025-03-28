package kevat25.ohjelmistoprojekti1.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "tyontekijat") // Taulu, johon Tyontekija-luokan instanssit tallennetaan
public class Tyontekija {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Tyontekija-id generoidaan automaattisesti tietokannan avulla
    @Column(name = "tyontekija_id") // Taulun sarake, jossa säilytetään työntekijän id:tä
    private Long tyontekijaId;

    @JsonIgnore
    @ManyToOne // Määrittää, että työntekijällä on linkki postinumeroon
    @JoinColumn(name = "postinumero") // Liittää postinumeron työntekijään
    private Postinumero postinumero;

    @NotBlank (message= "Työntekijällä tulee olla postiosoite")
    @Size(max=50, message = "Katuosoite voi olla enintään 50 merkkiä")
    private String katuosoite;

    @NotBlank (message = "Työntekijällä tulee olla etunimi")
    @Size(max=50, message = "Etunimi voi olla enintään 50 merkkiä")
    private String etunimi;

    @NotBlank (message = "Työntekijällä tulee olla sukunimi")
    @Size(max=50, message = "Sukunimi voi olla enintään 50 merkkiä")
    private String sukunimi;

    @NotBlank (message = "Työntekijällä tulee olla sähköposti")
    @Email (message = "Sähköpostin tulee olla oikeassa muodossa")
    @Column(unique=true)
    @Size(max=254, message = "Sähköposti voi olla enintään 254 merkkiä")
    private String email;

    @NotBlank (message = "Työntekijällä tulee olla puhelinnumero")
    @Size(max=15, message = "Puhelinnumero voi olla enintään 15 merkkiä")
    private String puhnro;

    @NotNull (message = "Työntekijällä tulee olla salasana")
    @Size(min=8, max=60, message = "Salasana voi olla enintään 60 merkkiä")
    private String bcrypthash;

    // Liittää työntekijät myynteihin; poistaa myynnit, jos työntekijä poistetaan
    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tyontekija")
    private List<Myynti> myynnit;

    public Tyontekija() {
    }

    public Tyontekija(Postinumero postinumero, String katuosoite,
            String etunimi, String sukunimi, String email, String puhnro, String bcrypthash) {
        this.postinumero = postinumero;
        this.katuosoite = katuosoite;
        this.etunimi = etunimi;
        this.sukunimi = sukunimi;
        this.email = email;
        this.puhnro = puhnro;
        this.bcrypthash = bcrypthash;
    }

    // Getterit ja setterit työntekijän tiedoille
    public Long getTyontekijaId() {
        return tyontekijaId;
    }

    public void setTyontekijaId(Long tyontekijaId) {
        this.tyontekijaId = tyontekijaId;
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

    public List<Myynti> getMyynnit() {
        return myynnit;
    }

    public void setMyynnit(List<Myynti> myynnit) {
        this.myynnit = myynnit;
    }

    /*
     * Ehdollinen toString tulostaa kaikki tiedot jos postinumero on määritelty,
     * muissa tapauksissa tulostetaan vain työntekijän perustiedot.
     */
    @Override
    public String toString() {
        if (this.postinumero != null) {
            return "Tyontekijat [tyontekijaId=" + tyontekijaId + ", postinumerot=" + this.getPostinumero()
                    + ", katuosoite="
                    + katuosoite + ", etunimi=" + etunimi + ", sukunimi=" + sukunimi
                    + ", email=" + email + ", puhnro=" + puhnro + ", bcrypthash=" + bcrypthash + "]";
        } else {
            return "Tyontekijat [tyontekijaId=" + tyontekijaId + ", katuosoite=" + katuosoite + ", etunimi=" + etunimi
                    + ", sukunimi=" + sukunimi
                    + ", email=" + email + ", puhnro=" + puhnro + ", bcrypthash=" + bcrypthash + "]";
        }
    }

}
