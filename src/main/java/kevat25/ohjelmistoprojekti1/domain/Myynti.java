package kevat25.ohjelmistoprojekti1.domain;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "myynnit") // Määrittää taulun, johon Myynti-luokan instanssit tallennetaan
public class Myynti {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // Myynti-id generoidaan automaattisesti tietokannan avulla
    @Column(name = "myynti_id") // Taulun sarake, jossa säilytetään myynti-id:tä
    private Long myyntiId;

    @ManyToOne // Määrittää, että tämä on monen myynnin ja yhden työntekijän välinen suhde
    @JoinColumn(name = "tyontekija_id") // Liittää työntekijän myyntiin työntekijä-id:n kautta
    @JsonBackReference
    @NotNull (message= "Myynnin tulee olla sidottu työntekijään")
    private Tyontekija tyontekija;

    @NotNull (message = "Myynnin aika tulee merkitä")
    private LocalDateTime myyntiaika;

    @Email (message = "Sähköpostiosoitteen pitää olla oikeassa muodossa")
    @Size(max=254, message = "Sähköpostiosoite voi olla maksimissaan 254 merkkiä")
    private String email;

    // Liittää liput myyntiin; kaikki liput poistetaan, kun myynti poistetaan
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "myynti")
    @JsonIgnore
    private List<Lippu> liput;

    public Myynti() {
    }

    public Myynti(LocalDateTime myyntiaika, String email, Tyontekija tyontekija) {
        this.myyntiaika = myyntiaika;
        this.email = email;
        this.tyontekija = tyontekija;
    }

    // Getterit ja setterit myyntitapahtuman tiedoille
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

    public Tyontekija getTyontekija() {
        return tyontekija;
    }

    public void setTyontekija(Tyontekija tyontekija) {
        this.tyontekija = tyontekija;
    }

    public List<Lippu> getLiput() {
        return liput;
    }

    public void setLiput(List<Lippu> liput) {
        this.liput = liput;
    }

    /*
     * Ehdollinen toString tulostaa kaikki tiedot jos työntekijä on määritelty,
     * muissa tapauksissa pelkästään myyntitapahtuman perustiedot
     */
    @Override
    public String toString() {
        if (this.tyontekija != null) {
            return "Myynnit [myyntiId=" + myyntiId + ", myyntiaika=" + myyntiaika + ", email=" + email + ", tyontekija="
                    + this.getTyontekija() + "]";
        } else {
            return "Myynnit [myyntiId=" + myyntiId + ", myyntiaika=" + myyntiaika + ", email=" + email + "]";
        }
    }

    // PrePersist-menetelmä, joka asettaa myyntiajan ennen tietokantaan
    // tallentamista
    @PrePersist
    public void ennenTallennusta() {
        if (myyntiaika == null) {
            myyntiaika = LocalDateTime.now(); // Asetetaan nykyinen aika, jos myyntiaikaa ei ole annettu
        }
    }

}
