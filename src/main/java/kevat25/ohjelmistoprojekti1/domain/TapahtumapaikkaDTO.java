package kevat25.ohjelmistoprojekti1.domain;

public class TapahtumapaikkaDTO {
    private Long tapahtumapaikkaId;
    private String tapahtumapaikka;
    private String katuosoite;
    private String puhnro;
    private String email;
    private Integer kapasiteetti;
    private Postinumero postinumero;

    public TapahtumapaikkaDTO() {
        this.tapahtumapaikkaId = tapahtumapaikkaId;
        this.tapahtumapaikka = tapahtumapaikka;
        this.katuosoite = katuosoite;
        this.puhnro = puhnro;
        this.email = email;
        this.kapasiteetti = kapasiteetti;
        this.postinumero = postinumero;
    }

    // Getterit ja setterit

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
}
