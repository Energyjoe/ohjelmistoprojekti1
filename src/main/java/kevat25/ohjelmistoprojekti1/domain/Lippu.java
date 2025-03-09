package kevat25.ohjelmistoprojekti1.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "liput")
public class Lippu {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // Generoitu ID-numero lipulle
    @Column(name = "lippu_id")
    private Long lippuId;

    @ManyToOne
    @JoinColumn(name = "tapahtumalippu_id")
    private Tapahtumalippu tapahtumalippu;

    @ManyToOne(cascade = CascadeType.PERSIST)  // Käytetään vain PERSIST, jotta se ei jää looppiin
    @JoinColumn(name = "myynti_id")
    private Myynti myynti;

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
