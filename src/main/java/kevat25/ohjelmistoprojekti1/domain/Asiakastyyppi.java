package kevat25.ohjelmistoprojekti1.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Asiakastyyppi {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // tietokanta luo uniikin arvon pääavaimelle
    private Long asiakastyyppiId;
    private String asiakastyyppi; // esim opiskelija, aikuinen, eläkeläinen, lapsi

    // Getterit ja setterit

    public Asiakastyyppi(Long asiakastyyppiId, String asiakastyyppi) {
        this.asiakastyyppiId = asiakastyyppiId;
        this.asiakastyyppi = asiakastyyppi;
    }

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
