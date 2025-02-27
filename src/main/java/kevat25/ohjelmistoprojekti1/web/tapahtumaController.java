package kevat25.ohjelmistoprojekti1.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kevat25.ohjelmistoprojekti1.domain.Tapahtuma;
import kevat25.ohjelmistoprojekti1.domain.TapahtumaRepository;



@RestController
@RequestMapping("/tapahtumat")
public class tapahtumaController {

    private final TapahtumaRepository tapahtumaRepository;
    
public tapahtumaController(TapahtumaRepository tapahtumaRepository) {
    this.tapahtumaRepository = tapahtumaRepository;
}


    @PostMapping("/lisaaTapahtuma")
    public ResponseEntity<Tapahtuma> uusiTapahtuma(@RequestBody Tapahtuma uusiTapahtuma) {

        // Tallennetaan tapahtuma repositoryyn
        Tapahtuma tallennettuTapahtuma = tapahtumaRepository.save(uusiTapahtuma);

        //Palauttaa HTTP-vastauksen
        return ResponseEntity.status(HttpStatus.CREATED).body(tallennettuTapahtuma);
    }

    @DeleteMapping("/{tapahtuma_id}")
    public ResponseEntity<Void> poistaTapahtuma(@PathVariable Long tapahtuma_id) {

        // Tarkistetaan, löytyykö annetulla id:llä tapahtuma
        if (!tapahtumaRepository.existsById(tapahtuma_id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        // Poistetaan tapahtuma id:n perusteella
        tapahtumaRepository.deleteById(tapahtuma_id);

        // Palautetaan 204 No Content
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
  }


