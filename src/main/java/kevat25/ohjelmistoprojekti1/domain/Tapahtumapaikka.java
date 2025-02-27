package kevat25.ohjelmistoprojekti1.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import java.util.List;
import jakarta.persistence.CascadeType;


@Entity
@Table(name = "tapahtumapaikat")
public class Tapahtumapaikka {

    //tapahtumapaikkaId
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "tapahtumapaikka_id")
    private Long tapahtumapaikkaId;

    //tapahtumapaikka, katuosoite, puhnro, email
    private String tapahtumapaikka, katuosoite, puhnro, email; 

    //postinumero
    @ManyToOne
    @JoinColumn(name = "postinumero")
    private Postinumero postinumero;

    //OneToMany-viittaus Tapahtumat-tauluun
    @OneToMany (cascade = CascadeType.ALL, mappedBy = "tapahtumaId")
    private List<Tapahtuma> tapahtumat;

    //parametriton konstruktori
    public Tapahtumapaikka() {
    }

    //parametrillinen konstruktori
    public Tapahtumapaikka (Long tapahtumapaikkaId, String tapahtumapaikka, String katuosoite, String puhnro, String email, Integer kapasiteetti, Postinumero postinumero) {
        this.tapahtumapaikkaId = tapahtumapaikkaId;
        this.tapahtumapaikka = tapahtumapaikka;
        this.katuosoite = katuosoite;
        this.puhnro = puhnro;
        this.email = email;
        this.postinumero = postinumero;
    }

    //getterit ja setterit

    public Long getTapahtumapaikkaId() {
        return tapahtumapaikkaId;
    }

    public void setTapahtumapaikkaId(Long tapahtumapaikkaId) {
        this.tapahtumapaikkaId = tapahtumapaikkaId;
    }

    public String getTapahtumapaikka() {
        return tapahtumapaikka;
    }

    public void setTapahtumapaikka(String tapahtumapaikka) {
        this.tapahtumapaikka = tapahtumapaikka;
    }

    public String getKatuosoite() {
        return katuosoite;
    }

    public void setKatuosoite(String katuosoite) {
        this.katuosoite = katuosoite;
    }

    public String getPuhnro() {
        return puhnro;
    }

    public void setPuhnro(String puhnro) {
        this.puhnro = puhnro;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public Postinumero getPostinumero() {
        return postinumero;
    }

    public void setPostinumero(Postinumero postinumero) {
        this.postinumero = postinumero;
    }

    @Override
    public String toString() {
        return "Tapahtumapaikat{" +
                "tapahtumapaikkaId=" + tapahtumapaikkaId +
                ", tapahtumapaikka='" + tapahtumapaikka + '\'' +
                ", katuosoite='" + katuosoite + '\'' +
                ", puhnro='" + puhnro + '\'' +
                ", email='" + email + '\'' +
                ", postinumero=" + postinumero +
                '}';
    }



}
