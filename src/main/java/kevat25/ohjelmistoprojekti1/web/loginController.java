package kevat25.ohjelmistoprojekti1.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import kevat25.ohjelmistoprojekti1.domain.LoginDTO;
import kevat25.ohjelmistoprojekti1.domain.Tyontekija;
import kevat25.ohjelmistoprojekti1.domain.TyontekijaRepository;
import kevat25.ohjelmistoprojekti1.service.JwtService;
import kevat25.ohjelmistoprojekti1.service.SalasanaTarkistusService;

@RestController
@RequestMapping("/login")
public class loginController {

    private final JwtService jwtService;
    private final SalasanaTarkistusService salasanaTarkistusService;
    private final TyontekijaRepository tyontekijaRepository;

    public loginController(JwtService jwtService, SalasanaTarkistusService salasanaTarkistusService, TyontekijaRepository tyontekijaRepository) {
        this.jwtService = jwtService;
        this.salasanaTarkistusService = salasanaTarkistusService;
        this.tyontekijaRepository = tyontekijaRepository;
    }

    @PostMapping()
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDto) {

        //Haetaan työntekijä tietokannasta
        Tyontekija tyontekija = tyontekijaRepository.findByEmail(loginDto.getEmail())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Virheellinen sähköposti tai salasana"));

        //Tarkistetaan annettu salasana
        boolean salasanaok = salasanaTarkistusService.tarkistaSalasana(loginDto.getSalasana(), tyontekija.getBcrypthash());
        if (!salasanaok) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Virheellinen sähköposti tai salasana");
        }

        //Luodaan ja palautetaan token
        String token = jwtService.generateToken(loginDto.getEmail());

        return ResponseEntity.ok(token);
    }
}
