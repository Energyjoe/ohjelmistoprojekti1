package kevat25.ohjelmistoprojekti1.service;

import java.util.List;
import java.util.Map;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kevat25.ohjelmistoprojekti1.domain.Lippu;
import kevat25.ohjelmistoprojekti1.domain.LippuRepository;
import kevat25.ohjelmistoprojekti1.domain.Myynti;
import kevat25.ohjelmistoprojekti1.domain.MyyntiDTO;
import kevat25.ohjelmistoprojekti1.domain.MyyntiRaporttiDTO;
import kevat25.ohjelmistoprojekti1.domain.MyyntiRepository;

@Service
public class MyyntiRaporttiService {

    @Autowired
    private MyyntiRepository myyntiRepository;

    private final LippuRepository lippuRepository;

    @Autowired
    public MyyntiRaporttiService(LippuRepository lippuRepository) {
        this.lippuRepository = lippuRepository;
    }

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

    // Tapahtumakohtainen raportti
    public List<MyyntiRaporttiDTO> haeMyyntiraporttiTapahtumalle(Long tapahtumaId) {
        List<Lippu> liput = lippuRepository.findByTapahtumaId(tapahtumaId);

        Map<String, List<Lippu>> ryhmitelty = liput.stream()
                .collect(Collectors.groupingBy(l -> l.getTapahtumalippu().getAsiakastyyppi().getAsiakastyyppi()));

        List<MyyntiRaporttiDTO> raportti = new ArrayList<>();

        for (Map.Entry<String, List<Lippu>> entry : ryhmitelty.entrySet()) {
            String asiakastyyppi = entry.getKey();
            Long maara = (long) entry.getValue().size();
            Double summa = entry.getValue().stream()
                    .mapToDouble(l -> l.getTapahtumalippu().getHinta().doubleValue())
                    .sum();

            raportti.add(new MyyntiRaporttiDTO(asiakastyyppi, maara, summa));
        }

        return raportti;
    }

    public List<Myynti> getMyyntiByTapahtuma(Long tapahtumaId) {
        return myyntiRepository.findMyyntiByTapahtuma(tapahtumaId);
    }

}