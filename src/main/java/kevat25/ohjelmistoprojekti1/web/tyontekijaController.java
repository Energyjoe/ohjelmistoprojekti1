package kevat25.ohjelmistoprojekti1.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import kevat25.ohjelmistoprojekti1.domain.SalasanaUpdateDTO;
import kevat25.ohjelmistoprojekti1.domain.TyontekijaCreateDTO;
import kevat25.ohjelmistoprojekti1.domain.TyontekijaResponseDTO;
import kevat25.ohjelmistoprojekti1.domain.TyontekijaUpdateDTO;
import kevat25.ohjelmistoprojekti1.service.TyontekijaService;



@RestController
@RequestMapping("/tyontekijat")
public class tyontekijaController {

    @Autowired
    private TyontekijaService tyontekijaService;

    //Lisää työntekijä
    @PostMapping("/")
    public ResponseEntity<TyontekijaResponseDTO> createTyontekija(@Valid @RequestBody TyontekijaCreateDTO createDto) {
        
        TyontekijaResponseDTO response = tyontekijaService.createTyontekija(createDto);        
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    

    // Muokkaa työntekijää
    @PatchMapping("/{tyontekijaId}")
    public ResponseEntity<TyontekijaResponseDTO> updateTyontekija(@PathVariable Long tyontekijaId, @Valid @RequestBody TyontekijaUpdateDTO updateDto) {
        
        TyontekijaResponseDTO response = tyontekijaService.updateTyontekija(tyontekijaId, updateDto);
        return ResponseEntity.ok(response);

    }

    //Vaihda salasana
    @PutMapping("/vaihda-salasana/{tyontekijaId}")
    public ResponseEntity<String> updateSalasana(@PathVariable Long tyontekijaId, @Valid @RequestBody SalasanaUpdateDTO salasanaDto) {

        tyontekijaService.updateSalasana(tyontekijaId, salasanaDto);
        return ResponseEntity.ok("Salasana vaihdettu onnistuneesti");
    }

}
