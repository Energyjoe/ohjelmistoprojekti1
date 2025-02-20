package kevat25.ohjelmistoprojekti1.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;



@Entity

public class Lippu {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) //Generoitu ID-numero lipulle
    private long lippuId;

@ManyToOne
@JoinColumn(name="tapahtumalippuId")
private long tapahtumalippu;

@ManyToOne
@JoinColumn(name="myyntiId")
private long myynti;

@GeneratedValue(strategy = GenerationType.SEQUENCE) //Generoitu numerosarja tarkistuskoodiksi 
private long tarkistuskoodi;

//Getterit ja setterit

public long getTapahtumalippu() {
    return tapahtumalippu;
}

public void setTapahtumalippu(long tapahtumalippu) {
    this.tapahtumalippu = tapahtumalippu;
}

public long getMyynti() {
    return myynti;
}

public void setMyynti(long myynti) {
    this.myynti = myynti;
}

public long getTarkistuskoodi() {
    return tarkistuskoodi;
}

public void setTarkistuskoodi(long tarkistuskoodi) {
    this.tarkistuskoodi = tarkistuskoodi;
}

}


