package kevat25.ohjelmistoprojekti1.domain;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "liput")
public class Lippu {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // Generoitu ID-numero lipulle
    @Column(name = "lippu_id")
    private Long lippuId;

    @ManyToOne
    @JoinColumn(name = "tapahtumalippu_id")
    @NotNull(message = "Tapahtumalippu on pakollinen tieto")
    private Tapahtumalippu tapahtumalippu;

    @ManyToOne(cascade = CascadeType.PERSIST) // Käytetään vain PERSIST, jotta se ei jää looppiin
    @JoinColumn(name = "myynti_id")
    @NotNull(message = "Myynti on pakollinen tieto")
    private Myynti myynti;

    @NotNull(message = "Tarkistuskoodi on välttämätön tieto")
    @Size(min = 8, max = 8, message = "Koodin pitää olla kahdeksan merkkiä pitkä")
    private String tarkistuskoodi;

    // Parametriton konstruktori
    public Lippu() {
    }

    // Getterit ja setterit

    public Lippu(Tapahtumalippu tapahtumalippu, Myynti myynti, String tarkistuskoodi) {
        this.tapahtumalippu = tapahtumalippu;
        this.myynti = myynti;
        this.tarkistuskoodi = tarkistuskoodi;
    }

    public Long getLippuId() {
        return lippuId;
    }

    public void setLippuId(Long lippuId) {
        this.lippuId = lippuId;
    }

    public Tapahtumalippu getTapahtumalippu() {
        return tapahtumalippu;
    }

    public void setTapahtumalippu(Tapahtumalippu tapahtumalippu) {
        this.tapahtumalippu = tapahtumalippu;
    }

    public Myynti getMyynti() {
        return myynti;
    }

    public void setMyynti(Myynti myynti) {
        this.myynti = myynti;
    }

    public String getTarkistuskoodi() {
        return tarkistuskoodi;
    }

    public void setTarkistuskoodi(String tarkistuskoodi) {
        this.tarkistuskoodi = tarkistuskoodi;
    }

    @Override
    public String toString() {
        return "Lippu [lippuId=" + lippuId + ", tapahtumalippu=" + tapahtumalippu + ", myynti=" + myynti
                + ", tarkistuskoodi=" + tarkistuskoodi + "]";
    }

}
