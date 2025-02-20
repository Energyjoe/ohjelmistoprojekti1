package kevat25.ohjelmistoprojekti1.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

// vaihdoin long tapahtumalippu -tyyppiset viittaukset viittauksiksi muiden luokkien entiteetteihin
// -Samuli 2025-02-20

@Entity
@Table(name = "liput")
public class Lippu {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // Generoitu ID-numero lipulle
    private Long lippuId;

    @ManyToOne
    @JoinColumn(name = "tapahtumalippu_id")
    private Tapahtumalippu tapahtumalippu;

    @ManyToOne
    @JoinColumn(name = "myynti_id")
    private Myynti myynti;

    // @GeneratedValue on tarkoitettu käytettäväksi vain yhdessä @Id -annotaation
    // kanssa pääavaimen generoimiseksi.
    // Kommentoin alla olevan rivin siksi pois. -Samuli 2025-02-20
    // @GeneratedValue(strategy = GenerationType.SEQUENCE) //Generoitu numerosarja
    // tarkistuskoodiksi
    private long tarkistuskoodi;

    // Getterit ja setterit

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

    public long getTarkistuskoodi() {
        return tarkistuskoodi;
    }

    public void setTarkistuskoodi(long tarkistuskoodi) {
        this.tarkistuskoodi = tarkistuskoodi;
    }



}
