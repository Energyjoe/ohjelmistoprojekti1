package kevat25.ohjelmistoprojekti1.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import jakarta.persistence.EntityNotFoundException;
import kevat25.ohjelmistoprojekti1.domain.Asiakastyyppi;
import kevat25.ohjelmistoprojekti1.domain.Lippu;
import kevat25.ohjelmistoprojekti1.domain.LippuDTO;
import kevat25.ohjelmistoprojekti1.domain.LippuRepository;
import kevat25.ohjelmistoprojekti1.domain.Tapahtumalippu;

import kevat25.ohjelmistoprojekti1.domain.Myynti;
import kevat25.ohjelmistoprojekti1.domain.Tapahtuma;
import kevat25.ohjelmistoprojekti1.domain.TapahtumaRepository;
import kevat25.ohjelmistoprojekti1.domain.TapahtumalippuRepository;
import kevat25.ohjelmistoprojekti1.domain.Tyontekija;
import kevat25.ohjelmistoprojekti1.domain.TyontekijaRepository;
import kevat25.ohjelmistoprojekti1.domain.MyyntiRepository;

//Tämä Service class yhdistää dataa eri entityistä LippuDTO:hon repositorien avulla.
@Service
public class LippuService {

    @Autowired
    private LippuRepository lippurepo; // Täältä haetaan myynti_id, johon lippu kuuluu, tarkistuskoodi ja
                                       // tapahtumalippu_id jonka kautta saadaan kyseisen tapahtuman tiedot

    @Autowired                                   
    private TapahtumaRepository tapahtumarepo; //Täältä haetaan tapahtuman kapasiteetti

    @Autowired
    private TapahtumalippuRepository tapahtumalippurepo; //Täältä haetaan tapahtumalippu_id:n perusteella asiakastyyppi ja hinta

    @Autowired
    private TyontekijaRepository tyontekijaRepository; //Täältä haetaan työntekijä, joka myi lipun

    @Autowired
    private MyyntiRepository myyntiRepository; 


    public LippuDTO getLipputiedot(Long lippuId) {

        // Luodaan tyhjä LippuDTO objekti
        LippuDTO lippuDTO = new LippuDTO();

        // Haetaan lippu id:n perusteella
        Lippu lippu = lippurepo.findById(lippuId)
                .orElseThrow(() -> new EntityNotFoundException("Ticket not found with ID: " + lippuId));
        lippuDTO.setTarkistuskoodi(lippu.getTarkistuskoodi()); // Haetaan lipusta tarkistuskoodi ja viedään se DTO:lle
        lippuDTO.setLippuId(lippuId); // Viedään lippuId lippuDTO:hon

        // Haetaan lippuun linkitetty myyntiobjekti
        Myynti myynti = lippu.getMyynti();
        if (myynti != null) {
            lippuDTO.setMyyntiId(myynti.getMyyntiId()); // Mikäli myynti löytyy, täytetään lippuDTO:n myynti_id myynnin
                                                        // ID:llä
        }

        // Haetaan lippuun liitetty tapahtumalippu
        Tapahtumalippu tapahtumalippu = lippu.getTapahtumalippu();
        if (tapahtumalippu != null) {
            lippuDTO.setTapahtumalippuId(tapahtumalippu.getTapahtumalippuId()); // Mikäli tapahtumalippu löytyy,
                                                                                // täytetään lippuDTO:n tietoja
            lippuDTO.setHinta(tapahtumalippu.getHinta()); // Haetaan hinta tapahtumalipusta
        }

        // Haetaan lippuun liitetty asiakastyyppi
        Asiakastyyppi asiakastyyppi = tapahtumalippu.getAsiakastyyppi(); // Haetaan tapahtumalippuun liitetty
                                                                         // asiakastyyppi
        if (asiakastyyppi != null) {
            lippuDTO.setAsiakastyyppi(asiakastyyppi.getAsiakastyyppi());

        }

        // Haetaan lippuun liitetty tapahtuma
        Tapahtuma tapahtuma = tapahtumalippu.getTapahtuma();
        if (tapahtuma != null) {
            lippuDTO.setTapahtumanNimi(tapahtuma.getTapahtumaNimi()); // Haetaan tapahtuman nimi ja viedään DTO:hon
            lippuDTO.setAlkuaika(tapahtuma.getAloitusaika()); // Haetaan tapahtuman aloitusaika ja viedään DTO:hon
        }

        return lippuDTO;
    }

    // satunnaisen kahdeksan merkin pituisen tarkistuskoodin generaattori

    public static String generoiTarkistuskoodi() {
        String tarkistuskoodi = "";
        String merkit = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        for (int i = 0; i < 8; i++) {
            int random = (int) (Math.random() * merkit.length());
            tarkistuskoodi += merkit.charAt(random);
        }
        return tarkistuskoodi;
    }

    public static String generoiUniikkiTarkistuskoodi(Long tapahtumaId, LippuRepository lippuRepository) {
        String tarkistuskoodi;
        do {
            tarkistuskoodi = generoiTarkistuskoodi();
        } while (lippuRepository.existsByTapahtumaAndTarkistuskoodi(tapahtumaId, tarkistuskoodi));

        return tarkistuskoodi;
    }

    public int liputJaljella(Long tapahtumaId) {

        Optional<Tapahtuma> tapahtumaOpt = tapahtumarepo.findById(tapahtumaId);

        if (tapahtumaOpt.isPresent()) {
            Tapahtuma tapahtuma = tapahtumaOpt.get();
            Integer kapasiteetti = tapahtuma.getKapasiteetti();

            System.out.println("Tapahtuma found: " + tapahtuma.getTapahtumaId());
            System.out.println("Kapasiteetti: " + kapasiteetti); 
            if (kapasiteetti == null) {
                throw new RuntimeException("Tapahtumalle ei ole määritelty kapasiteettia");
            }

            long myydyt = lippurepo.countByTapahtumaId(tapahtumaId);
            int jaljella = kapasiteetti - (int) myydyt;
            return jaljella;
        } else {
            throw new RuntimeException("Tapahtumaa ei löytynyt");
        }
    }
    
 
    }


