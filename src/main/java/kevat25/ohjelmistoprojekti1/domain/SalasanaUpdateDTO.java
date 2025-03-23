package kevat25.ohjelmistoprojekti1.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class SalasanaUpdateDTO {

    @NotBlank(message = "Vanha salasana ei voi olla tyhjä")
    @Size(min = 8, max = 60, message = "Vanhan salasanan on oltava 8-60 merkkiä pitkä")
    private String vanhaSalasana;

    @NotBlank(message = "Uusi salasana ei voi olla tyhjä")
    @Size(min = 8, max = 60, message = "Uuden salasanan on oltava 8-60 merkkiä pitkä")
    private String uusiSalasana;

    public SalasanaUpdateDTO() {
    }

    public SalasanaUpdateDTO(String vanhaSalasana, String uusiSalasana) {
        this.vanhaSalasana = vanhaSalasana;
        this.uusiSalasana = uusiSalasana;
    }

    public String getVanhaSalasana() {
        return vanhaSalasana;
    }

    public void setVanhaSalasana(String vanhaSalasana) {
        this.vanhaSalasana = vanhaSalasana;
    }

    public String getUusiSalasana() {
        return uusiSalasana;
    }

    public void setUusiSalasana(String uusiSalasana) {
        this.uusiSalasana = uusiSalasana;
    }

    
}
