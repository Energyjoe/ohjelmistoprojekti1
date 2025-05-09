package kevat25.ohjelmistoprojekti1.domain;

//Palauttaa työntekijän tiedot ilman salasanaa.
public class TyontekijaResponseDTO {
    private Long tyontekijaId;
    private String etunimi;
    private String sukunimi;
    private String email;
    private String puhnro;
    private String katuosoite;
    private String postinumero;
    private String paikkakunta;

    public TyontekijaResponseDTO(Tyontekija t) {
        this.tyontekijaId = t.getTyontekijaId();
        this.etunimi = t.getEtunimi();
        this.sukunimi = t.getSukunimi();
        this.email = t.getEmail();
        this.puhnro = t.getPuhnro();
        this.katuosoite = t.getKatuosoite();
        this.postinumero = t.getPostinumero().getPostinumero();
        this.paikkakunta = t.getPostinumero().getPaikkakunta();
    }

    public TyontekijaResponseDTO() {
        // Tyhjä konstruktori testausta ja sarjallistamista varten
        // (esim. JSON-muotoon muuntamista)
    }

    public Long getTyontekijaId() {
        return tyontekijaId;
    }

    public void setTyontekijaId(Long tyontekijaId) {
        this.tyontekijaId = tyontekijaId;
    }

    public String getEtunimi() {
        return etunimi;
    }

    public void setEtunimi(String etunimi) {
        this.etunimi = etunimi;
    }

    public String getSukunimi() {
        return sukunimi;
    }

    public void setSukunimi(String sukunimi) {
        this.sukunimi = sukunimi;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPuhnro() {
        return puhnro;
    }

    public void setPuhnro(String puhnro) {
        this.puhnro = puhnro;
    }

    public String getKatuosoite() {
        return katuosoite;
    }

    public void setKatuosoite(String katuosoite) {
        this.katuosoite = katuosoite;
    }

    public String getPostinumero() {
        return postinumero;
    }

    public void setPostinumero(String postinumero) {
        this.postinumero = postinumero;
    }

    public String getPaikkakunta() {
        return paikkakunta;
    }

    public void setPaikkakunta(String paikkakunta) {
        this.paikkakunta = paikkakunta;
    }

}
