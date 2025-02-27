package kevat25.ohjelmistoprojekti1.domain;

import jakarta.persistence.Entity;

// import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import jakarta.persistence.CascadeType;

@Entity
@Table(name = "postinumerot")
public class Postinumero {
    @Id
    private String postinumero;

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

    
    
}
