package kevat25.ohjelmistoprojekti1.domain;

import java.util.List;

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

@Entity
@Table(name = "tyontekijat")
public class Tyontekija {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "tyontekija_id")
    private Long tyontekijaId;

    @ManyToOne
    @JoinColumn(name = "postinumero")
    private Postinumero postinumero;

    private String katuosoite;
    private String etunimi;
    private String sukunimi;
    private String email;
    private String puhnro;
    private String bcrypthash;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tyontekija")
    private List<Myynti> myynnit;

    public Tyontekija() {
    }

    public Tyontekija(Long tyontekijaId, Postinumero postinumero, String katuosoite,
            String etunimi, String sukunimi, String email, String puhnro, String bcrypthash) {
        this.tyontekijaId = tyontekijaId;
        this.postinumero = postinumero;
        this.katuosoite = katuosoite;
        this.etunimi = etunimi;
        this.sukunimi = sukunimi;
        this.email = email;
        this.puhnro = puhnro;
        this.bcrypthash = bcrypthash;
    }

    public Long getTyontekijaId() {
        return tyontekijaId;
    }

    public void setTyontekijaId(Long tyontekijaId) {
        this.tyontekijaId = tyontekijaId;
    }

    public Postinumero getPostinumerot() {
        return postinumero;
    }

    public void setPostinumerot(Postinumero postinumero) {
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

    @Override
    public String toString() {
        if (this.postinumero != null) {
            return "Tyontekijat [tyontekijaId=" + tyontekijaId + ", postinumerot=" + this.getPostinumerot() + ", katuosoite="
                    + katuosoite + ", etunimi=" + etunimi + ", sukunimi=" + sukunimi
                    + ", email=" + email + ", puhnro=" + puhnro + ", bcrypthash=" + bcrypthash + "]";
        } else {
            return "Tyontekijat [tyontekijaId=" + tyontekijaId + ", katuosoite=" + katuosoite + ", etunimi=" + etunimi
                    + ", sukunimi=" + sukunimi
                    + ", email=" + email + ", puhnro=" + puhnro + ", bcrypthash=" + bcrypthash + "]";
        }
    }

}
