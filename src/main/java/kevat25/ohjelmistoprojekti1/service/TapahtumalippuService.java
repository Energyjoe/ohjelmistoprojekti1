package kevat25.ohjelmistoprojekti1.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import jakarta.persistence.EntityNotFoundException;
import kevat25.ohjelmistoprojekti1.domain.*;

@Service
public class TapahtumalippuService {

    private final LippuRepository lippuRepository;

    @Autowired
    private TapahtumalippuRepository tapahtumalippuRepository;

    @Autowired
    private TapahtumaRepository tapahtumaRepository;

    @Autowired
    private AsiakastyyppiRepository asiakastyyppiRepository;

    TapahtumalippuService(TapahtumalippuRepository tapahtumalippuRepository, LippuRepository lippuRepository) {
        this.tapahtumalippuRepository = tapahtumalippuRepository;
        this.lippuRepository = lippuRepository;
    }

    // Lisätään tapahtumalippu tapahtumaan
    public List<String> addTapahtumaliput(Long tapahtumaId, List<Tapahtumalippu> tapahtumaliput) {
        Optional<Tapahtuma> tapahtumaOptional = tapahtumaRepository.findById(tapahtumaId);
        if (!tapahtumaOptional.isPresent()) {
            throw new EntityNotFoundException("Tapahtumaa ei löytynyt.");
        }
        Tapahtuma tapahtuma = tapahtumaOptional.get();

        List<Tapahtumalippu> savedTapahtumaliput = new ArrayList<>();
        List<String> conflictMessages = new ArrayList<>();

        for (Tapahtumalippu tapahtumalippu : tapahtumaliput) {
            Optional<Asiakastyyppi> asiakastyyppiOptional = asiakastyyppiRepository
                    .findById(tapahtumalippu.getAsiakastyyppi().getAsiakastyyppiId());
            if (!asiakastyyppiOptional.isPresent()) {
                conflictMessages.add(
                        "Asiakastyyppiä ei löydy id:llä " + tapahtumalippu.getAsiakastyyppi().getAsiakastyyppiId());
                continue; // Siirry seuraavaan tapahtumalippuun
            }
            Asiakastyyppi asiakastyyppi = asiakastyyppiOptional.get();

            boolean exists = tapahtumalippuRepository.existsByTapahtumaAndAsiakastyyppi(tapahtuma, asiakastyyppi);
            if (exists) {
                conflictMessages.add(
                        "Tapahtumalippu asiakastyyppille " + asiakastyyppi.getAsiakastyyppi() + " on jo olemassa.");
            } else {
                tapahtumalippu.setTapahtuma(tapahtuma);
                tapahtumalippu.setAsiakastyyppi(asiakastyyppi);
                savedTapahtumaliput.add(tapahtumalippu);
            }
        }

        if (!savedTapahtumaliput.isEmpty()) {
            tapahtumalippuRepository.saveAll(savedTapahtumaliput);
        }

        return conflictMessages;
    }

    // Haetaan tapahtumaan liittyvät tapahtumaliput
    public List<Tapahtumalippu> haeLiput(Long tapahtumaId) {
        Optional<Tapahtuma> tapahtumaOpt = tapahtumaRepository.findById(tapahtumaId);
        if (tapahtumaOpt.isPresent()) {
            Tapahtuma tapahtuma = tapahtumaOpt.get();
            return tapahtumalippuRepository.findByTapahtuma(tapahtuma);
        } else {
            throw new EntityNotFoundException("Tapahtumaa ei löytynyt tällä id:llä " + tapahtumaId);
        }
    }

    public Optional<Tapahtumalippu> haeTapahtumalippu(Long tapahtumalippuId) {
        return tapahtumalippuRepository.findById(tapahtumalippuId);
    }

    public Tapahtumalippu paivitaTapahtumalippu(Long tapahtumalippuId, Tapahtumalippu uusiLippu) {
        return tapahtumalippuRepository.findById(tapahtumalippuId).map(tapahtumalippu -> {
            tapahtumalippu.setAsiakastyyppi(uusiLippu.getAsiakastyyppi());
            tapahtumalippu.setHinta(uusiLippu.getHinta());
            return tapahtumalippuRepository.save(tapahtumalippu); // tallennetaan muutokset
        }).orElseThrow(() -> new EntityNotFoundException("Tapahtumalippua ei löytynyt"));
    }

    public void poistaTapahtumalippu(Long tapahtumalippuId) {
        // Tarkistetaan, löytyykö tapahtumalippu
        if (!tapahtumalippuRepository.existsById(tapahtumalippuId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Tapahtumalippu ei löytynyt ID:llä " + tapahtumalippuId);
        }

        // Poistetaan tapahtumalippu
        tapahtumalippuRepository.deleteById(tapahtumalippuId);
    }

}
