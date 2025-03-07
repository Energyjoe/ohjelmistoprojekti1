package kevat25.ohjelmistoprojekti1.domain;

public class TapahtumalippuDTO {

    private Long tapahtumalippuId;
    private String tapahtumanNimi;
    private Double hinta;

    // Getterit ja setterit

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

    public Double getHinta() {
        return hinta;
    }

    public void setHinta(Double hinta) {
        this.hinta = hinta;
    }

    @Override
    public String toString() {
        return "TapahtumalippuDTO [tapahtumalippuId=" + tapahtumalippuId + ", tapahtumanNimi=" + tapahtumanNimi + ", hinta=" + hinta + "]";
    }
}
