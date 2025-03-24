package kevat25.ohjelmistoprojekti1.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;
import kevat25.ohjelmistoprojekti1.domain.SalasanaUpdateDTO;
import kevat25.ohjelmistoprojekti1.domain.Tyontekija;
import kevat25.ohjelmistoprojekti1.domain.TyontekijaCreateDTO;
import kevat25.ohjelmistoprojekti1.domain.TyontekijaRepository;
import kevat25.ohjelmistoprojekti1.domain.TyontekijaResponseDTO;
import kevat25.ohjelmistoprojekti1.domain.TyontekijaUpdateDTO;
import kevat25.ohjelmistoprojekti1.service.TyontekijaService;




@RestController
@RequestMapping("/tyontekijat")
public class tyontekijaController {

    private final TyontekijaRepository tyontekijaRepository;

    @Autowired
    private TyontekijaService tyontekijaService;

    public tyontekijaController(TyontekijaRepository tyontekijaRepository) {
        this.tyontekijaRepository = tyontekijaRepository;
    }

    //Lisää työntekijä
    @PostMapping("/")
    public ResponseEntity<TyontekijaResponseDTO> createTyontekija(@Valid @RequestBody TyontekijaCreateDTO createDto) {
        
        TyontekijaResponseDTO response = tyontekijaService.createTyontekija(createDto);        
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    //Hae työntekijän tiedot Id:n perusteella
    @GetMapping("/{tyontekijaId}")
    public ResponseEntity<TyontekijaResponseDTO> getTyontekijaById(@PathVariable Long tyontekijaId) {
        
        TyontekijaResponseDTO response = tyontekijaService.getTyontekijaById(tyontekijaId);
        return ResponseEntity.ok(response);
    }
    
    //Hae kaikki työntekijät
    @GetMapping("/")
    public List<TyontekijaResponseDTO> getAllMyynnit() {
        
        List<TyontekijaResponseDTO> tyontekijat = tyontekijaService.getAllTyontekijat(); 
        if (tyontekijat.isEmpty()) {
            return new ArrayList<>();
        }
        return tyontekijat;
    }

    // Muokkaa työntekijää
    @PatchMapping("/{tyontekijaId}")
    public ResponseEntity<TyontekijaResponseDTO> updateTyontekija(@PathVariable Long tyontekijaId, @Valid @RequestBody TyontekijaUpdateDTO updateDto) {
        
        TyontekijaResponseDTO response = tyontekijaService.updateTyontekija(tyontekijaId, updateDto);
        return ResponseEntity.ok(response);

    }

    //Vaihda salasana
    @PutMapping("/{tyontekijaId}/salasana")
    public ResponseEntity<String> updateSalasana(@PathVariable Long tyontekijaId, @Valid @RequestBody SalasanaUpdateDTO salasanaDto) {

        tyontekijaService.updateSalasana(tyontekijaId, salasanaDto);
        return ResponseEntity.ok("Salasana vaihdettu onnistuneesti");
    }

    //Poista työntekijä
    @DeleteMapping("/{tyontekijaId}")
    @ResponseBody
    public ResponseEntity<String> deleteTyontekija(@PathVariable Long tyontekijaId) {

        Optional<Tyontekija> tyontekija = tyontekijaRepository.findById(tyontekijaId);
        if(tyontekija.isPresent()) {
            tyontekijaRepository.delete(tyontekija.get());
            return ResponseEntity.noContent().build();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Työntekijää ei löytynyt");
        }
    }

}
