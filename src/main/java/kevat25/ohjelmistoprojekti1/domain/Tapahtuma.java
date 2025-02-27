package kevat25.ohjelmistoprojekti1.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.FetchType;
import java.time.LocalDateTime;
import jakarta.persistence.CascadeType;
import java.util.List;

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

    // Sarake kuvaus -.-.-.-.-.-.-.-.-.-
    @Column(name = "kuvaus")
    private String tapahtumaKuvaus;

    //Sarakkeet aloitusaika ja lopetusaika -.-.-.-.-.-.-.-.-.-
    private LocalDateTime aloitusaika;
    private LocalDateTime lopetusaika;

    // Sarake tapahtumapaikkaId -.-.-.-.-.-.-.-.-.-
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tapahtumapaikka_id")
    private Tapahtumapaikka tapahtumapaikka;

    //OneToMany-yhteys Tapahtumat-tauluun -.-.-.-.-.-.-.-.-.-
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tapahtumalippuId")
    private List<Tapahtumalippu> tapahtumaliput;

     //Parametriton konstruktori -.-.-.-.-.-.-.-.-.-
     public Tapahtuma() {
    }

    //Parametrillinen konstruktori -.-.-.-.-.-.-.-.-.-
    public Tapahtuma(String tapahtumaNimi, String tapahtumaKuvaus, LocalDateTime aloitusaika, LocalDateTime lopetusaika, Tapahtumapaikka tapahtumapaikka) {
        this.tapahtumaNimi = tapahtumaNimi;
        this.tapahtumaKuvaus = tapahtumaKuvaus;
        this.aloitusaika = aloitusaika;
        this.lopetusaika = lopetusaika;
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

    @Override
    public String toString() {
        return "Tapahtumat{" +
                "tapahtumaId=" + tapahtumaId +
                ", tapahtumaNimi='" + tapahtumaNimi + '\'' +
                ", tapahtumaKuvaus='" + tapahtumaKuvaus + '\'' +
                ", aloitusaika=" + aloitusaika +
                ", lopetusaika=" + lopetusaika +
                ", tapahtumapaikka=" + (tapahtumapaikka != null ? tapahtumapaikka.getTapahtumapaikka() : "ei määritetty") +
                '}';
    }
    




    
}
