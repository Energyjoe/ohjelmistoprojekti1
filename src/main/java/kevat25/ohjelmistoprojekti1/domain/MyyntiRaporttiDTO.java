package kevat25.ohjelmistoprojekti1.domain;


public class MyyntiRaporttiDTO {
    private String asiakastyyppi;
    private Long lukumaara;
    private Double kokonaishinta;

    // --- TÄMÄ KONSTRUKTORI ---
    public MyyntiRaporttiDTO(String asiakastyyppi, Long lukumaara, Double kokonaishinta) {
        this.asiakastyyppi = asiakastyyppi;
        this.lukumaara = lukumaara;
        this.kokonaishinta = kokonaishinta;
    }

    // Getterit ja setterit
    public String getAsiakastyyppi() {
        return asiakastyyppi;
    }

    public void setAsiakastyyppi(String asiakastyyppi) {
        this.asiakastyyppi = asiakastyyppi;
    }

    public Long getLukumaara() {
        return lukumaara;
    }

    public void setLukumaara(Long lukumaara) {
        this.lukumaara = lukumaara;
    }

    public Double getKokonaishinta() {
        return kokonaishinta;
    }

    public void setKokonaishinta(Double kokonaishinta) {
        this.kokonaishinta = kokonaishinta;
    }
}