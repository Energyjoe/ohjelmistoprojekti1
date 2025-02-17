package kevat25.ohjelmistoprojekti1.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.FetchType;
import java.time.LocalDateTime;

@Entity
public class Tapahtumat {

    // Sarake tapahtumaId -.-.-.-.-.-.-.-.-.-
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long tapahtumaId;

    // Sarake tapahtuma -.-.-.-.-.-.-.-.-.-
    private String tapahtumaNimi;

    // Sarake kuvaus -.-.-.-.-.-.-.-.-.-
    private String tapahtumaKuvaus;

    //Sarakkeet aloitusaika ja lopetusaika -.-.-.-.-.-.-.-.-.-
    private LocalDateTime aloitusaika;
    private LocalDateTime lopetusaika;

    // Sarake tapahtumapaikkaId -.-.-.-.-.-.-.-.-.-
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tapahtumapaikkaId")
    private Tapahtumapaikat tapahtumapaikka;

     //Parametriton konstruktori -.-.-.-.-.-.-.-.-.-
     public Tapahtumat() {
    }

    //Parametrillinen konstruktori -.-.-.-.-.-.-.-.-.-
    public Tapahtumat(String tapahtumaNimi, String tapahtumaKuvaus, LocalDateTime aloitusaika, LocalDateTime lopetusaika, Tapahtumapaikat tapahtumapaikka) {
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

    public Tapahtumapaikat getTapahtumapaikka() {
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

    public void setTapahtumapaikka(Tapahtumapaikat tapahtumapaikka) {
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
