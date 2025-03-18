package kevat25.ohjelmistoprojekti1.web;

import org.springframework.web.bind.annotation.RestController;

import kevat25.ohjelmistoprojekti1.domain.AsiakastyyppiRepository;
import kevat25.ohjelmistoprojekti1.domain.Asiakastyyppi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/asiakastyypit")
public class asiakastyyppiController {

    private final SecurityFilterChain SecurityFilterChain;
    @Autowired
    private AsiakastyyppiRepository asiakastyyppiRepository;

    asiakastyyppiController(SecurityFilterChain SecurityFilterChain) {
        this.SecurityFilterChain = SecurityFilterChain;
    }

    // Hae kaikki asiakastyypit
    @GetMapping("/")
    @ResponseBody
    public List<Asiakastyyppi> getAsiakastyypit() {
        List<Asiakastyyppi> asiakastyypit = (List<Asiakastyyppi>) asiakastyyppiRepository.findAll();
        if (asiakastyypit.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Asiakastyyppiä ei löytynyt");
        }
        return asiakastyypit;
    }

    // Luo uusi asiakastyyppi
    @PostMapping("/")
    public ResponseEntity<Asiakastyyppi> uusiAsiakastyyppi(@RequestBody Asiakastyyppi asiakastyyppi) {
        Asiakastyyppi uusiAsiakastyyppi = asiakastyyppiRepository.save(asiakastyyppi);
        return new ResponseEntity<>(uusiAsiakastyyppi, HttpStatus.CREATED);
    }

    //Muokkaa asiakastyyppiä
    @PatchMapping("/{asiakastyyppiId}")
    public ResponseEntity<Asiakastyyppi> paivitaAsiakastyyppi(@PathVariable Long asiakastyyppiId, @RequestBody Asiakastyyppi paivitettyAsiakastyyppi) {
        return asiakastyyppiRepository.findById(asiakastyyppiId)
            .map(asiakastyyppi -> {
                asiakastyyppi.setAsiakastyyppi(paivitettyAsiakastyyppi.getAsiakastyyppi());
                Asiakastyyppi tallennettuAsiakastyyppi = asiakastyyppiRepository.save(asiakastyyppi);
                return new ResponseEntity<>(tallennettuAsiakastyyppi, HttpStatus.OK);
            })
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Asiakastyyppiä ei löytynyt"));
        }

    // Poista yksittäinen asiakastyyppi
    @DeleteMapping("/{asiakastyyppiId}")
    @ResponseBody
    public ResponseEntity<String> poistaAsiakastyyppi(@PathVariable Long asiakastyyppiId) {
        Optional<Asiakastyyppi> asiakastyyppi = asiakastyyppiRepository.findById(asiakastyyppiId);
        if (asiakastyyppi.isPresent()) {
            asiakastyyppiRepository.delete(asiakastyyppi.get());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Asiakastyyppi poistettu");
        } else {
            // Jos asiakastyyppiä ei löydy, palautetaan 404 Not Found
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Asiakastyyppiä ei löytynyt");
        }
    }

    // Metodi käsittelee tietokantavirheet
    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<String> handleDatabaseException(DataAccessException ex) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body("Tietokantayhteys epäonnistui. Yritä uudelleen myöhemmin.");
    }

    @ExceptionHandler(CannotCreateTransactionException.class)
    public ResponseEntity<String> handleCannotCreateTransactionException(CannotCreateTransactionException ex) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body("Tietokantayhteys epäonnistui. Yritä uudelleen myöhemmin.");
    }
}

