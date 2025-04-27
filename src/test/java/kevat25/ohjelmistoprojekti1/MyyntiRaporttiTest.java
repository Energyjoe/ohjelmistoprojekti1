package kevat25.ohjelmistoprojekti1;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.List;

import kevat25.ohjelmistoprojekti1.domain.Myynti;
import kevat25.ohjelmistoprojekti1.domain.MyyntiRepository;
import kevat25.ohjelmistoprojekti1.service.MyyntiRaporttiService;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
public class MyyntiRaporttiTest {

    @Autowired
    private MyyntiRaporttiService myyntiRaporttiService;

    @Autowired
    private MyyntiRepository myyntiRepository;

    @Test
    public void testGetMyyntiByDay() {
        LocalDate date = LocalDate.of(2024, 3, 2);
        myyntiRepository.save(new Myynti(date, "Product A", 10, 100.0)); // Ensure test data exists
        List<Myynti> myynnit = myyntiRaporttiService.getMyyntiByDay(date);
        assertNotNull(myynnit);
        assertFalse(myynnit.isEmpty());

    }
}