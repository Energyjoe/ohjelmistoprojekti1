package kevat25.ohjelmistoprojekti1.web;

import kevat25.ohjelmistoprojekti1.domain.Asiakastyyppi;
import kevat25.ohjelmistoprojekti1.domain.AsiakastyyppiRepository;
import kevat25.ohjelmistoprojekti1.domain.Tapahtuma;
import kevat25.ohjelmistoprojekti1.domain.TapahtumaRepository;
import kevat25.ohjelmistoprojekti1.domain.Tapahtumalippu;
import kevat25.ohjelmistoprojekti1.domain.TapahtumalippuRepository;
import kevat25.ohjelmistoprojekti1.service.TapahtumalippuService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tapahtumaliput") // Peruspolku
public class tapahtumalippuController {

    @Autowired
    private TapahtumalippuRepository tapahtumalippuRepository;

    @Autowired
    private TapahtumaRepository tapahtumaRepository;

    @Autowired
    private AsiakastyyppiRepository asiakastyyppiRepository;
    @Autowired
    private TapahtumalippuService tapahtumalippuService;

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
        return ResponseEntity.ok(liput);
    }

    // Hakee yksittäisen tapahtumalipun ID:n perusteella
    @GetMapping("/tapahtumalippu/{tapahtumalippuId}")
    public ResponseEntity<Tapahtumalippu> haeTapahtumalippu(@PathVariable Long tapahtumalippuId) {
        Optional<Tapahtumalippu> tapahtumalippu = tapahtumalippuService.haeTapahtumalippu(tapahtumalippuId);
        return tapahtumalippu.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Päivittää lipun tiedot
    @PutMapping("/{tapahtumalippuId}")
    public ResponseEntity<Tapahtumalippu> paivitaTapahtumalippu(
            @PathVariable Long tapahtumalippuId,
            @RequestBody Tapahtumalippu uusiLippu) {
        try {
            Tapahtumalippu paivitettyLippu = tapahtumalippuService.paivitaTapahtumalippu(tapahtumalippuId, uusiLippu);
            return ResponseEntity.ok(paivitettyLippu);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    /*
     * PUT JSON MALLI. Riittääkö patch ja pelkän hinnan muokkaus?
     * {
     * "tapahtumalippuId": 1,
     * "hinta": 35.00,
     * "asiakastyyppi": {
     * "asiakastyyppiId": 2,
     * "asiakastyyppi": "Aikuinen"
     * },
     * "tapahtuma": {
     * "tapahtumaId": 1,
     * "tapahtumaNimi": "HIFK - Kärpät",
     * "kapasiteetti": null,
     * "tapahtumaKuvaus": "Liiga ottelu",
     * "aloitusaika": "2024-03-02T17:00:00",
     * "lopetusaika": "2024-03-02T19:30:00"
     * }
     * }
     */

    // Poistaa tapahtumalipun
    @DeleteMapping("/{tapahtumalippuId}")
    public ResponseEntity<Void> poistaTapahtumalippu(@PathVariable Long tapahtumalippuId) {
        tapahtumalippuService.poistaTapahtumalippu(tapahtumalippuId);
        return ResponseEntity.noContent().build();
    }
}
