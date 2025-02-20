package kevat25.ohjelmistoprojekti1.domain;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "myynnit")
public class Myynti {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long myyntiId;

    @ManyToOne
    @JoinColumn(name = "tyontekijaId")
    private Tyontekija tyontekijat;

    private LocalDate myyntiaika;
    private String email;


    public Myynti() {
    }

    public Myynti(LocalDate myyntiaika, String email, Tyontekija tyontekijat) {
        this.myyntiaika = myyntiaika;
        this.email = email;
        this.tyontekijat = tyontekijat;
    }

    public Long getMyyntiId() {
        return myyntiId;
    }

    public void setMyyntiId(Long myyntiId) {
        this.myyntiId = myyntiId;
    }

    public LocalDate getMyyntiaika() {
        return myyntiaika;
    }

    public void setMyyntiaika(LocalDate myyntiaika) {
        this.myyntiaika = myyntiaika;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Tyontekija getTyontekijat() {
        return tyontekijat;
    }

    public void setTyontekijat(Tyontekija tyontekijat) {
        this.tyontekijat = tyontekijat;
    }

    @Override
    public String toString() {
        if (this.tyontekijat != null) {
        return "Myynnit [myyntiId=" + myyntiId + ", myyntiaika=" + myyntiaika + ", email=" + email + ", tyontekijat="
                + this.getTyontekijat() + "]";
        } else {
            return "Myynnit [myyntiId=" + myyntiId + ", myyntiaika=" + myyntiaika + ", email=" + email + "]";
        }
    }

    

    

}
