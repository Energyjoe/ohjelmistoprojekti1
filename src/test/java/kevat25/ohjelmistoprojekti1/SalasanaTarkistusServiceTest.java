package kevat25.ohjelmistoprojekti1;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import kevat25.ohjelmistoprojekti1.service.SalasanaTarkistusService;


public class SalasanaTarkistusServiceTest {

    @Test
    public void SalasanaOikeinTesti() {
        SalasanaTarkistusService service = new SalasanaTarkistusService();
        String salasana = "salasana123";
        String hashSalasana = new BCryptPasswordEncoder().encode(salasana);

        assertTrue(service.tarkistaSalasana(salasana, hashSalasana));
    }

    @Test 
    public void SalasanaVaarinTesti() {
        SalasanaTarkistusService service = new SalasanaTarkistusService();
        String salasana = "salasana123";
        String salasana2 = "salasana321";
        String hashSalasana = new BCryptPasswordEncoder().encode(salasana2);

        assertFalse(service.tarkistaSalasana(salasana, hashSalasana));
    }
    

}
