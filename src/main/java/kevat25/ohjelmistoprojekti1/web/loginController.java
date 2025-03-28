package kevat25.ohjelmistoprojekti1.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import kevat25.ohjelmistoprojekti1.domain.LoginDTO;
import kevat25.ohjelmistoprojekti1.domain.Tyontekija;
import kevat25.ohjelmistoprojekti1.domain.TyontekijaRepository;
import kevat25.ohjelmistoprojekti1.service.JwtService;
import kevat25.ohjelmistoprojekti1.service.SalasanaTarkistusService;

@RestController
public class loginController {

    private final JwtService jwtService;
    private final SalasanaTarkistusService salasanaTarkistusService;
    private final TyontekijaRepository tyontekijaRepository;

    public loginController(JwtService jwtService, SalasanaTarkistusService salasanaTarkistusService, TyontekijaRepository tyontekijaRepository) {
        this.jwtService = jwtService;
        this.salasanaTarkistusService = salasanaTarkistusService;
        this.tyontekijaRepository = tyontekijaRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@PathVariable Long tyontekijaId, @RequestBody LoginDTO loginDto) {

        //Haetaan työntekijä tietokannasta
        Tyontekija tyontekija = tyontekijaRepository.findById(tyontekijaId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Työntekijää ei löydy"));

        //Tarkistetaan annettu sähköposti
        if (!tyontekija.getEmail().equals(loginDto.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Virheellinen sähköposti tai salasana");
        }
        

        //Tarkistetaan annettu salasana
        boolean salasanaok = salasanaTarkistusService.tarkistaSalasana(loginDto.getSalasana(), tyontekija.getBcrypthash());
        if (!salasanaok) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Virheellinen sähköposti tai salasana");
        }

        //Luodaan ja palautetaan token
        String token = jwtService.generateToken(loginDto.getEmail());

        return ResponseEntity.ok(token);
    }
}
