package kevat25.ohjelmistoprojekti1.domain;

import java.time.LocalDateTime;
import java.util.List;

public class MyyntiDTO {

//Myynti-entitystä
private LocalDateTime myyntiaika;
private Tyontekija tyontekija;
private String email;
private Long myyntiId;

//LippuDTO:t lisätään tänne listana, jolloin saadaan jokaisen lipun yksittäiset tiedot haettua tätä kautta
private List<LippuDTO> liput;


//Getterit

public LocalDateTime getMyyntiaika() {
    return myyntiaika;
}

public Tyontekija getTyontekija() {
    return tyontekija;
}

public String getEmail() {
    return email;
}

public Long getMyyntiId() {
    return myyntiId;
}

//Setterit



public void setMyyntiaika (LocalDateTime myyntiaika) {
    this.myyntiaika = myyntiaika;
}

public void setTyontekija (Tyontekija tyontekija) {
    this.tyontekija = tyontekija;
}

public void setEmail (String email) {
    this.email = email;
}

public void setMyyntiId(Long myyntiId) {
    this.myyntiId = myyntiId;
}

//Parametriton konstruktori:
public  MyyntiDTO() {

}

//Konstruktori: 
public MyyntiDTO(LocalDateTime myyntiaika, Tyontekija tyontekija, 
String email, Long myyntiId, List<LippuDTO> liput) {
this.myyntiaika = myyntiaika;
this.tyontekija = tyontekija;
this.email = email;
this.myyntiId = myyntiId;
this.liput = liput;
}


}


