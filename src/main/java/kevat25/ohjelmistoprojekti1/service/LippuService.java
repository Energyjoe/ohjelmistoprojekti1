package kevat25.ohjelmistoprojekti1.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import kevat25.ohjelmistoprojekti1.domain.Asiakastyyppi;
import kevat25.ohjelmistoprojekti1.domain.AsiakastyyppiRepository;
import kevat25.ohjelmistoprojekti1.domain.Lippu;
import kevat25.ohjelmistoprojekti1.domain.LippuDTO;
import kevat25.ohjelmistoprojekti1.domain.LippuRepository;
import kevat25.ohjelmistoprojekti1.domain.Tapahtumalippu;
import kevat25.ohjelmistoprojekti1.domain.Myynti;
import kevat25.ohjelmistoprojekti1.domain.Tapahtuma;
import kevat25.ohjelmistoprojekti1.domain.TapahtumaRepository;
import kevat25.ohjelmistoprojekti1.domain.TapahtumalippuRepository;

//Tämä Service class yhdistää dataa eri entityistä LippuDTO:hon repositorien avulla.
@Service
public class LippuService {

    @Autowired
    private LippuRepository lippurepo; // Täältä haetaan myynti_id, johon lippu kuuluu, tarkistuskoodi ja
                                       // tapahtumalippu_id jonka kautta saadaan kyseisen tapahtuman tiedot

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

}
