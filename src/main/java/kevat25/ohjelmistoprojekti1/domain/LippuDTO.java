package kevat25.ohjelmistoprojekti1.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class LippuDTO {


    private Long lippuId;

    @NotNull (message = "Tarkistuskoodi on pakollinen tieto")
    @Size(min=8, max=8, message = "Tarkistuskoodin tulee olla 8 merkkiä pitkä")
    private String tarkistuskoodi;

    @NotNull (message = "Myynnin id-numero on pakollinen tieto")
    private Long myyntiId; // Viite myynti-id:hen

    @NotNull (message = "Tapahtumalipun id-numero on pakollinen tieto")
    private Long tapahtumalippuId; // Viite tapahtumalippu-id:hen

    // Tapahtuma-entitystä
    @NotBlank (message = "Tapahtumalla pitää olla nimi")
    @Size(max=100, message = "Tapahtuman nimi voi olla enintään 100 merkkiä")
    private String tapahtumanNimi;

    @NotNull (message = "Tapahtumalla pitää olla alkuaika")
    @Future (message = "Tapahtuman alkuajan pitää olla tulevaisuudessa")
    private LocalDateTime alkuaika;

    // Tapahtumalippu-entitystä
    @NotNull (message = "Tapahtumalipulla pitää olla hinta")
    private BigDecimal hinta;

    // Asiakastyyppi-entitystä
    @NotNull (message = "Asiakastyypillä pitää määritellä")
    @Size(max=20, message = "Asiakastyypin nimi voi olla enintään 20 merkkiä")
    private String asiakastyyppi;

    // Parametriton konstruktori
    public LippuDTO() {
    }

    // Parametrillinen konstruktori
    public LippuDTO(Long lippuId, String tarkistuskoodi, Long myyntiId, Long tapahtumalippuId,
            String tapahtumanNimi, LocalDateTime alkuaika, BigDecimal hinta,
            String asiakastyyppi) {
        this.lippuId = lippuId;
        this.tarkistuskoodi = tarkistuskoodi;
        this.myyntiId = myyntiId;
        this.tapahtumalippuId = tapahtumalippuId;
        this.tapahtumanNimi = tapahtumanNimi;
        this.alkuaika = alkuaika;
        this.hinta = hinta;
        this.asiakastyyppi = asiakastyyppi;
    }

    // Getterit ja setterit

    public Long getLippuId() {
        return lippuId;
    }

    public void setLippuId(Long lippuId) {
        this.lippuId = lippuId;
    }

    public String getTarkistuskoodi() {
        return tarkistuskoodi;
    }

    public void setTarkistuskoodi(String tarkistuskoodi) {
        this.tarkistuskoodi = tarkistuskoodi;
    }

    public Long getMyyntiId() {
        return myyntiId;
    }

    public void setMyyntiId(Long myyntiId) {
        this.myyntiId = myyntiId;
    }

    public Long getTapahtumalippuId() {
        return tapahtumalippuId;
    }

    public void setTapahtumalippuId(Long tapahtumalippuId) {
        this.tapahtumalippuId = tapahtumalippuId;
    }

    public String getTapahtumanNimi() {
        return tapahtumanNimi;
    }

    public void setTapahtumanNimi(String tapahtumanNimi) {
        this.tapahtumanNimi = tapahtumanNimi;
    }

    public LocalDateTime getAlkuaika() {
        return alkuaika;
    }

    public void setAlkuaika(LocalDateTime alkuaika) {
        this.alkuaika = alkuaika;
    }

    public BigDecimal getHinta() {
        return hinta;
    }

    public void setHinta(BigDecimal hinta) {
        this.hinta = hinta;
    }

    public String getAsiakastyyppi() {
        return asiakastyyppi;
    }

    public void setAsiakastyyppi(String asiakastyyppi) {
        this.asiakastyyppi = asiakastyyppi;
    }
}
