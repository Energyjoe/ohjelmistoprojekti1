package kevat25.ohjelmistoprojekti1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import kevat25.ohjelmistoprojekti1.domain.Myynti;
import kevat25.ohjelmistoprojekti1.domain.Tyontekija;

@SpringBootTest
public class MyyntiTest {

    @Test
    void testMyynninLuominen() {
        Tyontekija tyontekija = new Tyontekija();
        LocalDateTime myyntiaika = LocalDateTime.now();
        String email = "testi@email.com";

        Myynti myynti = new Myynti(myyntiaika, email, tyontekija);

        assertEquals(email, myynti.getEmail());
        assertEquals(myyntiaika, myynti.getMyyntiaika());
        assertEquals(tyontekija, myynti.getTyontekija());
    }
}
