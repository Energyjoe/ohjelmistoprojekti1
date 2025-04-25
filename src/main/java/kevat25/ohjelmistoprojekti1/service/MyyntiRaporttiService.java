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

    //Päiväkohtainen raportti
    public List<Myynti> getMyyntiByDay(LocalDate date) {
        return myyntiRepository.findMyyntiByDay(date);
    }

}

