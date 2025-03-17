package kevat25.ohjelmistoprojekti1.domain;

public class LippuPostDTO {
    private Long myyntiId;
    private Long tapahtumalippuId;

    public LippuPostDTO() {
    }

    public LippuPostDTO(Long myyntiId, Long tapahtumalippuId) {
        this.myyntiId = myyntiId;
        this.tapahtumalippuId = tapahtumalippuId;
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

}
