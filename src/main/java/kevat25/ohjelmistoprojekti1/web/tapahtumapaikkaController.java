package kevat25.ohjelmistoprojekti1.web;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import kevat25.ohjelmistoprojekti1.domain.Postinumero;
import kevat25.ohjelmistoprojekti1.domain.Tapahtumapaikka;
import kevat25.ohjelmistoprojekti1.domain.TapahtumapaikkaDTO;
import kevat25.ohjelmistoprojekti1.domain.TapahtumapaikkaRepository;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RestController
@RequestMapping("/tapahtumapaikat")
public class tapahtumapaikkaController {

    @Autowired
    private TapahtumapaikkaRepository tapahtumapaikkaRepository;

    // Muunna entiteetti DTO:ksi
    private TapahtumapaikkaDTO toDTO(Tapahtumapaikka entity) {
        TapahtumapaikkaDTO dto = new TapahtumapaikkaDTO();
        dto.setTapahtumapaikkaId(entity.getTapahtumapaikkaId());
        dto.setTapahtumapaikka(entity.getTapahtumapaikka());
        dto.setKatuosoite(entity.getKatuosoite());
        dto.setPuhnro(entity.getPuhnro());
        dto.setEmail(entity.getEmail());
        dto.setKapasiteetti(entity.getKapasiteetti());

        if (entity.getPostinumero() != null) {
            dto.setPostinumero(entity.getPostinumero());
        }

        return dto;
    }

    // Muunna DTO entiteetiksi
    private Tapahtumapaikka fromDTO(TapahtumapaikkaDTO dto) {
        Tapahtumapaikka entity = new Tapahtumapaikka();
        entity.setTapahtumapaikka(dto.getTapahtumapaikka());
        entity.setKatuosoite(dto.getKatuosoite());
        entity.setPuhnro(dto.getPuhnro());
        entity.setEmail(dto.getEmail());
        entity.setKapasiteetti(dto.getKapasiteetti());

        if (dto.getPostinumero() != null) {
            Postinumero postinumero = new Postinumero();
            postinumero.setPostinumero(dto.getPostinumero().getPostinumero());
            postinumero.setPaikkakunta(dto.getPostinumero().getPaikkakunta());
            entity.setPostinumero(postinumero);
        }

        return entity;
    }

    // Hae kaikki tapahtumapaikat
    @GetMapping("/")
    @ResponseBody
    public List<TapahtumapaikkaDTO> getTapahtumapaikat() {
        List<Tapahtumapaikka> tapahtumapaikat = (List<Tapahtumapaikka>) tapahtumapaikkaRepository.findAll();
        if (tapahtumapaikat.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tapahtumapaikkoja ei löytynyt");
        }
        return tapahtumapaikat.stream()
                .map(this::toDTO) // Muunna kukin entiteetti DTO:ksi
                .collect(Collectors.toList());
    }

    @GetMapping("/{tapahtumapaikkaId}")
    @ResponseBody
    public ResponseEntity<TapahtumapaikkaDTO> getTapahtumapaikkaById(@PathVariable Long tapahtumapaikkaId) {
        Optional<Tapahtumapaikka> tapahtumapaikkaOpt = tapahtumapaikkaRepository.findById(tapahtumapaikkaId);

        if (tapahtumapaikkaOpt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Tapahtumapaikkaa ei löytynyt ID:llä " + tapahtumapaikkaId);
        }

        TapahtumapaikkaDTO dto = toDTO(tapahtumapaikkaOpt.get());
        return ResponseEntity.ok(dto);
    }

    // Luo uusi tapahtumapaikka
    @PostMapping("/")
    public ResponseEntity<TapahtumapaikkaDTO> uusiTapahtumapaikka(@RequestBody TapahtumapaikkaDTO dto) {
        Tapahtumapaikka uusiTapahtumapaikka = tapahtumapaikkaRepository.save(fromDTO(dto));
        return new ResponseEntity<>(toDTO(uusiTapahtumapaikka), HttpStatus.CREATED);
    }

    // Muokkaa tapahtumapaikkaa
    @PatchMapping("/{tapahtumapaikkaId}")
    public ResponseEntity<TapahtumapaikkaDTO> paivitaTapahtumapaikka(@PathVariable Long tapahtumapaikkaId,
            @RequestBody TapahtumapaikkaDTO dto) {

        Optional<Tapahtumapaikka> optionalTapahtumapaikka = tapahtumapaikkaRepository.findById(tapahtumapaikkaId);
        if (optionalTapahtumapaikka.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tapahtumapaikkaa ei löytynyt");
        }

        Tapahtumapaikka tp = optionalTapahtumapaikka.get();

        // Päivitä vain muut kentät, mutta älä postinumeroa
        if (dto.getTapahtumapaikka() != null)
            tp.setTapahtumapaikka(dto.getTapahtumapaikka());
        if (dto.getKatuosoite() != null)
            tp.setKatuosoite(dto.getKatuosoite());
        if (dto.getPuhnro() != null)
            tp.setPuhnro(dto.getPuhnro());
        if (dto.getEmail() != null)
            tp.setEmail(dto.getEmail());
        if (dto.getKapasiteetti() != null)
            tp.setKapasiteetti(dto.getKapasiteetti());

        // Älä päivitä postinumeroa
        // if(dto.getPostinumero() != null) tp.setPostinumero(dto.getPostinumero()); <--
        // Tämä jää pois

        Tapahtumapaikka paivitetty = tapahtumapaikkaRepository.save(tp);
        return ResponseEntity.ok(toDTO(paivitetty)); // Muunna päivitetty entiteetti DTO:ksi ja palauta
    }

    // Poista tapahtumapaikka
    @DeleteMapping("/{tapahtumapaikkaId}")
    @ResponseBody
    public ResponseEntity<String> poistaTapahtumapaikka(@PathVariable Long tapahtumapaikkaId) {
        Optional<Tapahtumapaikka> tapahtumapaikka = tapahtumapaikkaRepository.findById(tapahtumapaikkaId);
        if (tapahtumapaikka.isPresent()) {
            tapahtumapaikkaRepository.delete(tapahtumapaikka.get());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Tapahtumapaikka poistettu");
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tapahtumapaikka ei löytynyt");
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
