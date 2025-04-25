package kevat25.ohjelmistoprojekti1.web;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kevat25.ohjelmistoprojekti1.domain.Myynti;
import kevat25.ohjelmistoprojekti1.service.MyyntiRaporttiService;

@RestController
@RequestMapping("/raportit/myynti")
public class MyyntiRaporttiController {

    @Autowired
    private MyyntiRaporttiService myyntiRaporttiService;

    //Päiväkohtaisten raporttien nouto
    @GetMapping("/day")
    public List<Myynti> getMyyntiByDay(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return myyntiRaporttiService.getMyyntiByDay(date);
    }

}
