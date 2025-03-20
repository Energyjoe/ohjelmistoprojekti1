package kevat25.ohjelmistoprojekti1.web;

import kevat25.ohjelmistoprojekti1.domain.Tapahtumalippu;
import kevat25.ohjelmistoprojekti1.domain.TapahtumalippuRepository;
import kevat25.ohjelmistoprojekti1.service.TapahtumalippuService;

import org.hibernate.validator.internal.util.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.observation.ObservationProperties.Http;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import ch.qos.logback.classic.Logger;
import jakarta.persistence.EntityNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/tapahtumaliput") // Peruspolku
public class tapahtumalippuController {

    @Autowired
    private TapahtumalippuService tapahtumalippuService;

    @Autowired
    private TapahtumalippuRepository tapahtumalippuRepository;

    // POST-pyyntö, joka lisää useamman tapahtumalipun
    // POST-pyyntö, joka lisää useamman tapahtumalipun
    @PostMapping("/{tapahtumaId}")
    public ResponseEntity<?> addTapahtumaliput(@PathVariable Long tapahtumaId,
            @RequestBody List<Tapahtumalippu> tapahtumaliput) {
        List<String> conflictMessages = tapahtumalippuService.addTapahtumaliput(tapahtumaId, tapahtumaliput);

        if (!conflictMessages.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(conflictMessages);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(tapahtumaliput);
    }

    /*
     * POST JSON pyyntö:
     * [
     * {
     * "hinta": 25.00,
     * "asiakastyyppi": {
     * "asiakastyyppiId": 1
     * }
     * },
     * {
     * "hinta": 45.00,
     * "asiakastyyppi": {
     * "asiakastyyppiId": 2
     * }
     * },
     * {
     * "hinta": 30.00,
     * "asiakastyyppi": {
     * "asiakastyyppiId": 3
     * }
     * }
     * ]
     */

    // Hakee kaikki liput tietylle tapahtumalle
    @GetMapping("/{tapahtumaId}")
    public ResponseEntity<List<Tapahtumalippu>> haeLiput(@PathVariable Long tapahtumaId) {
        List<Tapahtumalippu> liput = tapahtumalippuService.haeLiput(tapahtumaId);
        if (liput.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Tapahtumalippuja ei löytynyt tapahtumaID:llä " + tapahtumaId);
        }
        return ResponseEntity.ok(liput);
    }

    // Hakee yksittäisen tapahtumalipun ID:n perusteella
    @GetMapping("/tapahtumalippu/{tapahtumalippuId}")
    public ResponseEntity<Tapahtumalippu> haeTapahtumalippu(@PathVariable Long tapahtumalippuId) {
        Optional<Tapahtumalippu> tapahtumalippu = tapahtumalippuService.haeTapahtumalippu(tapahtumalippuId);
        if (tapahtumalippu.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Tapahtumalippua ei löytynyt tapahtumalippuID:llä " + tapahtumalippuId);
        }
        return tapahtumalippu.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("/{tapahtumalippuId}")
    public ResponseEntity<Tapahtumalippu> paivitaTapahtumalippu(@PathVariable Long tapahtumalippuId,
            @RequestBody Map<String, Object> updates) {
        try {
            Tapahtumalippu paivitettyLippu = tapahtumalippuService.paivitaTapahtumalippu(tapahtumalippuId, updates);
            return ResponseEntity.ok(paivitettyLippu);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Poistaa tapahtumalipun
    @DeleteMapping("/{tapahtumalippuId}")
    @ResponseBody
    public ResponseEntity<String> poistaTapahtumalippu(@PathVariable Long tapahtumalippuId) {
        Optional<Tapahtumalippu> tapahtumalippu = tapahtumalippuRepository.findById(tapahtumalippuId);
        if (tapahtumalippu.isPresent()) {
            tapahtumalippuRepository.delete(tapahtumalippu.get());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Tapahtumalippu poistettu");
        } else {
            // Jos tapahtumalippua ei löydy, palautetaan 404 Not Found
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Tapahtumalippua ei löytynyt ID:llä " + tapahtumalippuId);
        }
    }

}
