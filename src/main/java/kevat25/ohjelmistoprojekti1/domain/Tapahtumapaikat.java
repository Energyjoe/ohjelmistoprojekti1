package kevat25.ohjelmistoprojekti1.domain;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Tapahtumapaikat {

    //tapahtumapaikkaId
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long tapahtumapaikkaId;

    //tapahtumapaikka, katuosoite, puhnro, email
    private String tapahtumapaikka, katuosoite, puhnro, email; 

    //kapasiteetti
    private int kapasiteetti;

    //postinumero
    @ManyToOne
    @JoinColumn(postinumero = "postinumero")
    private Postinumerot postinumero;

    //parametriton konstruktori
    public Tapahtumapaikat() {
    }

    //parametrillinen konstruktori
    public Tapahtumapaikat (Long tapahtumapaikkaId, String tapahtumapaikka, String katuosoite, String puhnro, String email, int kapasiteetti, Postinumerot postinumero) {
        this.tapahtumapaikkaId = tapahtumapaikkaId;
        this.tapahtumapaikka = tapahtumapaikka;
        this.katuosoite = katuosoite;
        this.puhnro = puhnro;
        this.email = email;
        this.kapasiteetti = kapasiteetti;
        this.postinumero = postinumero;
    }

    //getterit ja setterit

    public Long getTapahtumapaikkaId() {
        return tapahtumapaikkaId;
    }

    public void setTapahtumapaikkaId(Long tapahtumapaikkaId) {
        return this.tapahtumapaikkaId = tapahtumapaikkaId;
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

    public int getKapasiteetti() {
        return kapasiteetti;
    }

    public void setKapasiteetti(int kapasiteetti) {
        this.kapasiteetti = kapasiteetti;
    }

    public Postinumerot getPostinumero() {
        return postinumero;
    }

    public void setPostinumero(Postinumerot postinumero) {
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
                ", kapasiteetti=" + kapasiteetti +
                ", postinumero=" + postinumero +
                '}';
    }



}
