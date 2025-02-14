package kevat25.ohjelmistoprojekti1.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Asiakastyypit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // tietokanta luo uniikin arvon pääavaimelle
    private Long asiakastyyppiId;
    private String asiakastyyppi; // esim opiskelija, aikuinen, eläkeläinen, lapsi

    // Getterit ja setterit

    public Long getAsiakastyyppiId() {
        return asiakastyyppiId;
    }

    public void setAsiakastyyppiId(Long asiakastyyppiId) {
        this.asiakastyyppiId = asiakastyyppiId;
    }

    public String getAsiakastyyppi() {
        return asiakastyyppi;
    }

    public void setAsiakastyyppi(String asiakastyyppi) {
        this.asiakastyyppi = asiakastyyppi;
    }

    @Override
    public String toString() {
        return "Asiakastyypit{" +
                "asiakastyyppiId=" + asiakastyyppiId +
                ", asiakastyyppi='" + asiakastyyppi + '\'' +
                '}';
    }
}
