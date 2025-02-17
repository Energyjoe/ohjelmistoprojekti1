package kevat25.ohjelmistoprojekti1.domain;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Tapahtumaliput {

    // Sarake tapahtumalippuID ja hinta -.-.-
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long tapahtumalippuId;
    private BigDecimal hinta;

    // Sarake asiakastyyppiId -.-.-.-.-.-.-.-

    @ManyToOne
    @JoinColumn(name = "asiakastyyppiId")
    private Asiakastyypit asiakastyyppi;

    // Sarake tapahtumaId -.-.-.-.-.-.-.-.-.-

    @ManyToOne
    @JoinColumn(name = "tapahtumaId")
    private Tapahtumat tapahtuma;

    // Getterit -.-.-.-.-.-.-.-.-.-.-.-.-.-.-

    public Long getTapahtumalippuId() {
        return tapahtumalippuId;
    }

    public BigDecimal getHinta() {
        return hinta;
    }

    public Asiakastyypit getAsiakastyyppi() {
        return asiakastyyppi;
    }

    public Tapahtumat getTapahtuma() {
        return tapahtuma;
    }

    // Setterit -.-.-.-.-.-.-.-.-.-.-.-.-.-.-

    public void setTapahtumalippuId(Long tapahtumalippuId) {
        this.tapahtumalippuId = tapahtumalippuId;
    }

    public void setHinta(BigDecimal hinta) {
        this.hinta = hinta;
    }

    public void setAsiakastyyppi(Asiakastyypit asiakastyyppi) {
        this.asiakastyyppi = asiakastyyppi;
    }

    public void setTapahtuma(Tapahtumat tapahtuma) {
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
