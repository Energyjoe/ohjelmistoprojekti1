package kevat25.ohjelmistoprojekti1.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kevat25.ohjelmistoprojekti1.domain.Myynti;
import kevat25.ohjelmistoprojekti1.domain.MyyntiRepository;

@Service
public class MyyntiRaporttiService {

    @Autowired
    private MyyntiRepository myyntiRepository;

    // Päiväkohtainen raportti
    public List<Myynti> getMyyntiByDay(LocalDate date) {
        return myyntiRepository.findMyyntiByDay(date);
    }

    // Raportti valitulta aikaväliltä
    public List<Myynti> getMyyntiByDateRange(LocalDate startDate, LocalDate endDate) {
        // Muutetaan LocalDate-tyyppiset päivämäärät LocalDateTime-tyypiksi
        LocalDateTime startDateTime = startDate.atStartOfDay(); // Alkuajankohta (esim. 2024-02-01T00:00:00)
        LocalDateTime endDateTime = endDate.atTime(23, 59, 59); // Loppuajankohta (esim. 2024-12-31T23:59:59)

        return myyntiRepository.findMyyntiByDateRange(startDateTime, endDateTime);
    }

    // Työntekijäkohtainen raportti
    public List<Myynti> getMyyntiByTyontekija(Long tyontekijaId) {
        return myyntiRepository.findMyyntiByTyontekija(tyontekijaId);
    }

    // Työntekijäkohtainen raportti valitulta aikaväliltä
    public List<Myynti> getMyyntiByTyontekijaAndDateRange(Long tyontekijaId, LocalDate startDate, LocalDate endDate) {
        // Muutetaan LocalDate-tyyppiset päivämäärät LocalDateTime-tyypiksi
        LocalDateTime startDateTime = startDate.atStartOfDay(); // Alkuajankohta (esim. 2024-02-01T00:00:00)
        LocalDateTime endDateTime = endDate.atTime(23, 59, 59); // Loppuajankohta (esim. 2024-12-31T23:59:59)

        return myyntiRepository.findMyyntiByTyontekijaAndDateRange(tyontekijaId, startDateTime, endDateTime);
    }

}