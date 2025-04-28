package kevat25.ohjelmistoprojekti1.web;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import kevat25.ohjelmistoprojekti1.domain.Myynti;
import kevat25.ohjelmistoprojekti1.domain.MyyntiDTO;
import kevat25.ohjelmistoprojekti1.domain.MyyntiRaporttiDTO;
import kevat25.ohjelmistoprojekti1.service.MyyntiRaporttiService;

@RestController
@RequestMapping("/raportit/myynti")
public class MyyntiRaporttiController {

    @Autowired
    private MyyntiRaporttiService myyntiRaporttiService;

    // Päiväkohtaisten raporttien nouto
    @GetMapping("/day")
    public List<Myynti> getMyyntiByDay(
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return myyntiRaporttiService.getMyyntiByDay(date);
    }

    // Myynnit tietyltä aikaväliltä
    @GetMapping("/range")
    public List<Myynti> getMyyntiByDateRange(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return myyntiRaporttiService.getMyyntiByDateRange(startDate, endDate);
    }

    // Työntekijäkohtaisten raporttien nouto
    @GetMapping("/tyontekija")
    public List<Myynti> getMyyntiByTyontekija(
            @RequestParam("id") Long tyontekijaId) {
        return myyntiRaporttiService.getMyyntiByTyontekija(tyontekijaId);
    }

    // Työntekijäkohtaiset myynnit tietyltä aikaväliltä
    @GetMapping("/tyontekija/range")
    public List<Myynti> getMyyntiByTyontekijaAndDateRange(
            @RequestParam("tyontekijaId") Long tyontekijaId,
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return myyntiRaporttiService.getMyyntiByTyontekijaAndDateRange(tyontekijaId, startDate, endDate);
    }

    @GetMapping("/tapahtumaraportti")
    public List<MyyntiRaporttiDTO> haeMyyntiraporttiTapahtumalle(@RequestParam("tapahtumaId") Long tapahtumaId) {
        if (tapahtumaId == null || tapahtumaId <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid tapahtumaId.");
        }
        return myyntiRaporttiService.haeMyyntiraporttiTapahtumalle(tapahtumaId);
    }

    @GetMapping("/tapahtuma/myynnit")
    public List<Myynti> getMyyntiByTapahtuma(
            @RequestParam("tapahtumaId") Long tapahtumaId) {
        return myyntiRaporttiService.getMyyntiByTapahtuma(tapahtumaId);
    }

}
