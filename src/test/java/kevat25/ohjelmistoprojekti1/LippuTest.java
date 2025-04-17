package kevat25.ohjelmistoprojekti1;

import kevat25.ohjelmistoprojekti1.web.lippuController;
import kevat25.ohjelmistoprojekti1.domain.Lippu;
import kevat25.ohjelmistoprojekti1.domain.LippuRepository;
import kevat25.ohjelmistoprojekti1.domain.Myynti;
import kevat25.ohjelmistoprojekti1.domain.Tapahtumalippu;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.checkerframework.checker.units.qual.s;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


@DataJpaTest
@ExtendWith(MockitoExtension.class)
public class LippuTest {



@Test
void testLipunLuominen() {
    Tapahtumalippu tapahtumalippu = new Tapahtumalippu();
    Myynti myynti = new Myynti();
    String tarkistuskoodi = "ABC12345";
    
    Lippu lippu = new Lippu(tapahtumalippu, myynti, tarkistuskoodi);

    assertEquals(tapahtumalippu, lippu.getTapahtumalippu());
    assertEquals(myynti, lippu.getMyynti());
    assertEquals(tarkistuskoodi, lippu.getTarkistuskoodi());
    assertFalse(lippu.getTarkistettu());
}







}
