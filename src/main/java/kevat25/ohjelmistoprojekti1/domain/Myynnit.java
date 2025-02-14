package kevat25.ohjelmistoprojekti1.domain;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Myynnit {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long myyntiId;

    @ManyToOne
    @JoinColumn(name = "tyontekijaId")
    private Tyontekijat tyontekijat;

    private LocalDate myyntiaika;
    private String email;


    public Myynnit() {
    }

    public Myynnit(LocalDate myyntiaika, String email, Tyontekijat tyontekijat) {
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

    public Tyontekijat getTyontekijat() {
        return tyontekijat;
    }

    public void setTyontekijat(Tyontekijat tyontekijat) {
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
