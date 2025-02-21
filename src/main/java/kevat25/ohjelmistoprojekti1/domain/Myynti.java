package kevat25.ohjelmistoprojekti1.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
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
    @Column(name = "myynti_id")
    private Long myyntiId;

    @ManyToOne
    @JoinColumn(name = "tyontekija_id")
    private Tyontekija tyontekijat;

    private LocalDateTime myyntiaika;
    private String email;


    public Myynti() {
    }

    public Myynti(LocalDateTime myyntiaika, String email, Tyontekija tyontekijat) {
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

    public LocalDateTime getMyyntiaika() {
        return myyntiaika;
    }

    public void setMyyntiaika(LocalDateTime myyntiaika) {
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
