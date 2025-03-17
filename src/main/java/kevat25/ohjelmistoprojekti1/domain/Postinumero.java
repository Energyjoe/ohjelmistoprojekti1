package kevat25.ohjelmistoprojekti1.domain;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


@Entity
@Table(name = "postinumerot")
public class Postinumero {
    @Id
    @Size(min=5, max=5, message = "Postinumeron tulee olla 5 merkkiä")
    private String postinumero;

    @NotBlank (message = "Paikkakunnan nimi ei voi olla tyhjä")
    @Size(max=50, message = "Paikkakunnan nimi voi olla maksimissaan 50 merkkiä")
    private String paikkakunta;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "postinumero")
    private List<Tapahtumapaikka> tapahtumapaikat;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "postinumero")
    private List<Tyontekija> tyontekijat;

    public Postinumero() {
    }
    
    public Postinumero(String postinumero, String paikkakunta) {
        this.postinumero = postinumero;
        this.paikkakunta = paikkakunta;
    }

    public String getPostinumero() {
        return postinumero;
    }

    public void setPostinumero(String postinumero) {
        this.postinumero = postinumero;
    }

    public String getPaikkakunta() {
        return paikkakunta;
    }

    public void setPaikkakunta(String paikkakunta) {
        this.paikkakunta = paikkakunta;
    }

    public List<Tapahtumapaikka> getTapahtumapaikat() {
        return tapahtumapaikat;
    }

    public void setTapahtumapaikat(List<Tapahtumapaikka> tapahtumapaikat) {
        this.tapahtumapaikat = tapahtumapaikat;
    }

    public List<Tyontekija> getTyontekijat() {
        return tyontekijat;
    }

    public void setTyontekijat(List<Tyontekija> tyontekijat) {
        this.tyontekijat = tyontekijat;
    }

    @Override
    public String toString() {
        return "Postinumero [postinumero=" + postinumero + ", paikkakunta=" + paikkakunta + "]";
    }

    
    
}
