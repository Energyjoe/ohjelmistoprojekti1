package kevat25.ohjelmistoprojekti1.domain;

import java.util.List;

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
@Table(name = "tapahtumapaikat")
public class Tapahtumapaikka {

    //tapahtumapaikkaId
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "tapahtumapaikka_id")
    private Long tapahtumapaikkaId;

    //tapahtumapaikka, katuosoite, puhnro, email
    @NotBlank (message = "Tapahtumapaikalla tulee olla nimi")
    @Size(max=50, message = "Tapahtumapaikan nimi voi olla enintään 50 merkkiä")
    private String tapahtumapaikka;
    
    @NotBlank (message = "Tapahtumapaikalla tulee olla osoite")
    @Size(max=50, message = "Katuosoite voi olla enintään 50 merkkiä")
    private String katuosoite;
    
    @Size(max=15, message = "Puhelinnumero voi olla enintään 15 merkkiä")
    private String puhnro;
    
    @Email (message = "Sähköpostin tulee olla oikeassa muodossa")
    @Size (max=254, message = "Sähköposti voi olla enintään 254 merkkiä")
    private String email; 

    @NotNull (message = "Tapahtumapaikalla tulee olla kapasiteetti")
    private Integer kapasiteetti;

    //postinumero
    @ManyToOne
    @JoinColumn(name = "postinumero")
    //Poistettu @Size-annotaatio, koska se ei toimi Postinumero-oliolla
    private Postinumero postinumero;

    //OneToMany-viittaus Tapahtumat-tauluun
    @OneToMany (cascade = CascadeType.ALL, mappedBy = "tapahtumapaikka")
    @JsonManagedReference
    // https://www.baeldung.com/jackson-bidirectional-relationships-and-infinite-recursion
    private List<Tapahtuma> tapahtumat;

    //parametriton konstruktori
    public Tapahtumapaikka() {
    }

    //parametrillinen konstruktori
    public Tapahtumapaikka (String tapahtumapaikka, String katuosoite, String puhnro, String email, Integer kapasiteetti, Postinumero postinumero) {
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

    public Integer getKapasiteetti() {
        return kapasiteetti;
    }

    public void setKapasiteetti(Integer kapasiteetti) {
        this.kapasiteetti = kapasiteetti;
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
                ", kapasiteetti=" + kapasiteetti +
                ", postinumero=" + postinumero +
                '}';
    }



}
