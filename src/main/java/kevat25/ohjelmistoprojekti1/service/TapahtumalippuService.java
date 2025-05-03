package kevat25.ohjelmistoprojekti1.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
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

    // Lisätään tapahtumaliput tapahtumaan
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
                        "Tapahtumalippu asiakastyyppille " + asiakastyyppi.getAsiakastyyppi() + " ID:llä "
                                + asiakastyyppi.getAsiakastyyppiId() + " on jo olemassa.");
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

    //Lisätään yksittäinen tapahtumalippu tapahtumaan
  ////  public Tapahtumalippu lisaaTapahtumalippu(Long tapahtumaId, Tapahtumalippu tapahtumalippu) {
   ////     Optional<Tapahtuma> tapahtumaOptional = tapahtumaRepository.findById(tapahtumaId);
   //     if (!tapahtumaOptional.isPresent()) {
   //         throw new EntityNotFoundException("Tapahtumaa ei löytynyt.");
   //     }
   //     Tapahtuma tapahtuma = tapahtumaOptional.get();


    //    Optional<Asiakastyyppi> asiakastyyppiOptional = asiakastyyppiRepository
    //                .findById(tapahtumalippu.getAsiakastyyppi().getAsiakastyyppiId());
    //        if (!asiakastyyppiOptional.isPresent()) {
     //           throw new ResponseStatusException(HttpStatus.NOT_FOUND,
    //                    "Asiakastyyppiä ei löydy id:llä " + tapahtumalippu.getAsiakastyyppi().getAsiakastyyppiId());
    //        }
    //        Asiakastyyppi asiakastyyppi = asiakastyyppiOptional.get();

    //        boolean exists = tapahtumalippuRepository.existsByTapahtumaAndAsiakastyyppi(tapahtuma, asiakastyyppi);
    //        if (exists) {
    //            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
     //                   "Tapahtumalippu asiakastyyppille " + asiakastyyppi.getAsiakastyyppi() + " ID:llä "
    //                            + asiakastyyppi.getAsiakastyyppiId() + " on jo olemassa.");
    //        } else {
    //            tapahtumalippu.setTapahtuma(tapahtuma);
    //            tapahtumalippu.setAsiakastyyppi(asiakastyyppi);
     //           tapahtumalippuRepository.save(tapahtumalippu);
    //        }

   //         return tapahtumalippu;
   
   // }


    // Haetaan tapahtumaan liittyvät tapahtumaliput
    public List<Tapahtumalippu> haeLiput(Long tapahtumaId) {
        Optional<Tapahtuma> tapahtumaOpt = tapahtumaRepository.findById(tapahtumaId);
        if (tapahtumaOpt.isPresent()) {
            Tapahtuma tapahtuma = tapahtumaOpt.get();
            return tapahtumalippuRepository.findByTapahtuma(tapahtuma);
        } else {
            throw new ResourceNotFoundException("Tapahtumaa ei löytynyt tällä id:llä " + tapahtumaId);
        }
    }

    public Optional<Tapahtumalippu> haeTapahtumalippu(Long tapahtumalippuId) {
        return tapahtumalippuRepository.findById(tapahtumalippuId);
    }

    public Tapahtumalippu paivitaTapahtumalippu(Long tapahtumalippuId, Map<String, Object> updates) {
        return tapahtumalippuRepository.findById(tapahtumalippuId)
                .map(tapahtumalippu -> {
                    if (updates.containsKey("hinta")) {
                        BigDecimal hinta = new BigDecimal(updates.get("hinta").toString());
                        tapahtumalippu.setHinta(hinta);
                    }
                    // Voit lisätä muita kenttiä, jos ne ovat tarpeellisia
                    return tapahtumalippuRepository.save(tapahtumalippu);
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
