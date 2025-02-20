package kevat25.ohjelmistoprojekti1.domain;

import jakarta.persistence.Entity;

// import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "postinumerot")
public class Postinumero {
    @Id
    private String postinumero;

    @Column(name="paikkakunta")
    private String paikkakunta;

    // mapping OneToMany tai ManyToOne puuttuu

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
