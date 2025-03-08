package kevat25.ohjelmistoprojekti1.domain;

import java.math.BigDecimal;
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
@Table(name = "tapahtumaliput")
public class Tapahtumalippu {

    // Sarake tapahtumalippuID ja hinta -.-.-
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "tapahtumalippu_id")
    private Long tapahtumalippuId;
    private BigDecimal hinta; // Rahasummien tallentamiseen BigDecimal on suositeltavaa, koska se on tarkka ja
                              // estää pyöristysvirheitä, joita voi esiintyä double-tyypin kanssa.

    // Sarake asiakastyyppiId -.-.-.-.-.-.-.-

    @ManyToOne
    @JoinColumn(name = "asiakastyyppi_id")
    private Asiakastyyppi asiakastyyppi;

    // Sarake tapahtumaId -.-.-.-.-.-.-.-.-.-

    @ManyToOne
    @JoinColumn(name = "tapahtuma_id")
    private Tapahtuma tapahtuma;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tapahtumalippu")
    private List<Lippu> liput;

    // Getterit -.-.-.-.-.-.-.-.-.-.-.-.-.-.-

    public Long getTapahtumalippuId() {
        return tapahtumalippuId;
    }

    public BigDecimal getHinta() {
        return hinta;
    }

    public Asiakastyyppi getAsiakastyyppi() {
        return asiakastyyppi;
    }

    public Tapahtuma getTapahtuma() {
        return tapahtuma;
    }

    // Parametriton konstruktori
    public Tapahtumalippu() {
    }

    // Setterit -.-.-.-.-.-.-.-.-.-.-.-.-.-.-

    public void setTapahtumalippuId(Long tapahtumalippuId) {
        this.tapahtumalippuId = tapahtumalippuId;
    }

    public void setHinta(BigDecimal hinta) {
        this.hinta = hinta;
    }

    public void setAsiakastyyppi(Asiakastyyppi asiakastyyppi) {
        this.asiakastyyppi = asiakastyyppi;
    }

    public void setTapahtuma(Tapahtuma tapahtuma) {
        this.tapahtuma = tapahtuma;
    }

    @Override
    public String toString() {
        return "Tapahtumaliput{" +
                "tapahtumalippuId=" + tapahtumalippuId +
                ", hinta=" + hinta +
                ", asiakastyyppi=" + (asiakastyyppi != null ? asiakastyyppi.getAsiakastyyppi() : "ei määritetty") +
                ", tapahtuma=" + (tapahtuma != null ? tapahtuma.getTapahtumaNimi() : "ei määritetty") +
                '}';
    }

}
