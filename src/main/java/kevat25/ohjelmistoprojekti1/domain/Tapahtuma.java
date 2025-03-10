package kevat25.ohjelmistoprojekti1.domain;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tapahtumat")
public class Tapahtuma {

    // Sarake tapahtumaId -.-.-.-.-.-.-.-.-.-
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "tapahtuma_id")
    private Long tapahtumaId;

    // Sarake tapahtuma -.-.-.-.-.-.-.-.-.-
    @Column(name = "tapahtuma")
    private String tapahtumaNimi;

    // Kapasiteetti 
    private Integer kapasiteetti;

    // Sarake kuvaus -.-.-.-.-.-.-.-.-.-
    @Column(name = "kuvaus")
    private String tapahtumaKuvaus;

    //Sarakkeet aloitusaika ja lopetusaika -.-.-.-.-.-.-.-.-.-
    private LocalDateTime aloitusaika;
    private LocalDateTime lopetusaika;

    // Sarake tapahtumapaikkaId -.-.-.-.-.-.-.-.-.-
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tapahtumapaikka_id")
    @JsonBackReference
    // https://www.baeldung.com/jackson-bidirectional-relationships-and-infinite-recursion
    private Tapahtumapaikka tapahtumapaikka;

    //OneToMany-yhteys Tapahtumat-tauluun -.-.-.-.-.-.-.-.-.-
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tapahtuma")
    private List<Tapahtumalippu> tapahtumaliput;

     //Parametriton konstruktori -.-.-.-.-.-.-.-.-.-
     public Tapahtuma() {
    }

    //Parametrillinen konstruktori -.-.-.-.-.-.-.-.-.-
    public Tapahtuma(String tapahtumaNimi, String tapahtumaKuvaus, LocalDateTime aloitusaika, LocalDateTime lopetusaika, Integer kapasiteetti, Tapahtumapaikka tapahtumapaikka) {
        this.tapahtumaNimi = tapahtumaNimi;
        this.tapahtumaKuvaus = tapahtumaKuvaus;
        this.aloitusaika = aloitusaika;
        this.lopetusaika = lopetusaika;
        this.kapasiteetti = kapasiteetti;
        this.tapahtumapaikka = tapahtumapaikka;
    }

    // Getterit -.-.-.-.-.-.-.-
    public Long getTapahtumaId() {
        return tapahtumaId;
    }

    public String getTapahtumaNimi() {
        return tapahtumaNimi;
    }

    public String getTapahtumaKuvaus() {
        return tapahtumaKuvaus;
    }

    public LocalDateTime getAloitusaika() {
        return aloitusaika;
    }

    public LocalDateTime getLopetusaika() {
        return lopetusaika;
    }

    public Tapahtumapaikka getTapahtumapaikka() {
        return tapahtumapaikka;
    }

    public Integer getKapasiteetti() {
        return kapasiteetti;
    }

    // Setterit -.-.-.-.-.-.-.-.-.-

    public void setTapahtumaId(Long tapahtumaId) {
        this.tapahtumaId = tapahtumaId;
    }

    public void setTapahtumaNimi(String tapahtumaNimi) {
        this.tapahtumaNimi = tapahtumaNimi;
    }

    public void setTapahtumaKuvaus(String tapahtumaKuvaus) {
        this.tapahtumaKuvaus = tapahtumaKuvaus;
    }

    public void setAloitusaika(LocalDateTime aloitusaika) {
        this.aloitusaika = aloitusaika;
    }

    public void setLopetusaika(LocalDateTime lopetusaika) {
        this.lopetusaika = lopetusaika;
    }

    public void setTapahtumapaikka(Tapahtumapaikka tapahtumapaikka) {
        this.tapahtumapaikka = tapahtumapaikka;
    }

    public void setKapasiteetti (Integer kapasiteetti) {
        this.kapasiteetti = kapasiteetti;
    }

    @Override
    public String toString() {
        return "Tapahtumat{" +
                "tapahtumaId=" + tapahtumaId +
                ", tapahtumaNimi='" + tapahtumaNimi + '\'' +
                ", tapahtumaKuvaus='" + tapahtumaKuvaus + '\'' +
                ", aloitusaika=" + aloitusaika +
                ", lopetusaika=" + lopetusaika +
                ", kapasiteetti=" + kapasiteetti +
                ", tapahtumapaikka=" + (tapahtumapaikka != null ? tapahtumapaikka.getTapahtumapaikka() : "ei määritetty") +
                '}';
    }
    



    
}
