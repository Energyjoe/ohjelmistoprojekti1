package kevat25.ohjelmistoprojekti1.web;

import org.springframework.web.bind.annotation.RestController;

import kevat25.ohjelmistoprojekti1.domain.MyyntiRepository;
import kevat25.ohjelmistoprojekti1.domain.Tapahtuma;
import kevat25.ohjelmistoprojekti1.domain.TapahtumaRepository;
import kevat25.ohjelmistoprojekti1.domain.Tapahtumalippu;
import kevat25.ohjelmistoprojekti1.domain.Lippu;
import kevat25.ohjelmistoprojekti1.domain.LippuRepository;
import kevat25.ohjelmistoprojekti1.domain.Myynti;
import kevat25.ohjelmistoprojekti1.domain.LippuPostDTO;
import kevat25.ohjelmistoprojekti1.domain.TapahtumalippuRepository;
import kevat25.ohjelmistoprojekti1.service.LippuService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/liput")
public class lippuController {

    private final SecurityFilterChain SecurityFilterChain;
    @Autowired
    private MyyntiRepository myyntiRepository;
    @Autowired
    private TapahtumalippuRepository tapahtumalippuRepository;
    @Autowired
    private LippuRepository lippuRepository;

    lippuController(SecurityFilterChain SecurityFilterChain) {
        this.SecurityFilterChain = SecurityFilterChain;
    }

    @GetMapping("/")
    @ResponseBody
    public List<Lippu> getLiput() {
        return (List<Lippu>) lippuRepository.findAll();
    }

    @PostMapping("/")
    @ResponseBody
    public Lippu postLippu(@RequestBody LippuPostDTO request) {
        Long myyntiId = request.getMyyntiId();
        Long tapahtumalippuId = request.getTapahtumalippuId();

        Optional<Myynti> myynti = myyntiRepository.findById(myyntiId);
        Optional<Tapahtumalippu> tapahtumalippu = tapahtumalippuRepository.findById(tapahtumalippuId);

        if (tapahtumalippu.isPresent() && myynti.isPresent()) {
            Long tapahtumaId = tapahtumalippu.get().getTapahtuma().getTapahtumaId();
            String tarkistuskoodi = LippuService.generoiUniikkiTarkistuskoodi(tapahtumaId, lippuRepository);

            Lippu lippu = new Lippu(tapahtumalippu.get(), myynti.get(), tarkistuskoodi);
            lippuRepository.save(lippu);
            return lippu;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tapahtumaa tai myyntiä ei löytynyt");
    }

    @DeleteMapping("/{lippuId}")
    @ResponseBody
    public void deleteLippu(@PathVariable Long lippuId) {
        Optional<Lippu> lippu = lippuRepository.findById(lippuId);
        if (lippu.isPresent()) {
            lippuRepository.delete(lippu.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Lippua ei löytynyt");
        }
    }

}
