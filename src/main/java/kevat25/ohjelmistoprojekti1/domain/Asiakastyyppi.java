package kevat25.ohjelmistoprojekti1.domain;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "asiakastyypit")
public class Asiakastyyppi {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "asiakastyyppi_id") // Use @Column if the field name differs from the column name
    private Long asiakastyyppiId;

    @NotNull(message = "Asiakastyyppi on välttämätön tieto")
    @Size(max = 20)
    private String asiakastyyppi; // esim opiskelija, aikuinen, eläkeläinen, lapsi

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "asiakastyyppi") // cascade = mahdollistaa automaattisen
                                                                      // päivityksen ja poiston
    private List<Tapahtumalippu> tapahtumaliput;

    // Parametriton konstruktori
    public Asiakastyyppi() {
    }

    // Getterit ja setterit

    public Asiakastyyppi(String asiakastyyppi) {
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
