package kevat25.ohjelmistoprojekti1.domain;

public class LippuDTO {

    private Long lippuId;
    private String tarkistuskoodi;
    private Long tapahtumalippuId; // Viite tapahtumalippu-id:hen
    private Long myyntiId; // Viite myynti-id:hen

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

    public Long getTapahtumalippuId() {
        return tapahtumalippuId;
    }

    public void setTapahtumalippuId(Long tapahtumalippuId) {
        this.tapahtumalippuId = tapahtumalippuId;
    }

    public Long getMyyntiId() {
        return myyntiId;
    }

    public void setMyyntiId(Long myyntiId) {
        this.myyntiId = myyntiId;
    }

    @Override
    public String toString() {
        return "LippuDTO [lippuId=" + lippuId + ", tarkistuskoodi=" + tarkistuskoodi + ", tapahtumalippuId="
                + tapahtumalippuId + ", myyntiId=" + myyntiId + "]";
    }
}
