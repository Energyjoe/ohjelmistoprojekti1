package kevat25.ohjelmistoprojekti1.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import kevat25.ohjelmistoprojekti1.domain.Lippu;
import kevat25.ohjelmistoprojekti1.domain.LippuDTO;
import kevat25.ohjelmistoprojekti1.domain.LippuRepository;
import kevat25.ohjelmistoprojekti1.domain.Myynti;
import kevat25.ohjelmistoprojekti1.domain.MyyntiDTO;
import kevat25.ohjelmistoprojekti1.domain.MyyntiRepository;
import kevat25.ohjelmistoprojekti1.domain.Tyontekija;

@Service
public class MyyntiService {

    @Autowired
    private MyyntiRepository myyntiRepository;

    @Autowired
    private LippuRepository lippuRepository;

    @Autowired
    private LippuService lippuService; // Service, joka täyttää LippuDTO -objektin datalla

    // Etsii myyntitapahtuman id:n perusteella
    public MyyntiDTO getMyyntiById(Long myyntiId) {
        Myynti myynti = myyntiRepository.findById(myyntiId)
                .orElseThrow(() -> new EntityNotFoundException("Myyntiä ei löytynyt tällä id:llä: " + myyntiId));

        //Etsii kaikki kyseiseen myyntitapahtumaan liittyvät liput
        List<Lippu> liput = lippuRepository.findByMyyntiMyyntiId(myyntiId);  //Etsii liput myyntiId:n perusteella

        List<LippuDTO> lippudtot = new ArrayList();  //Luo tyhjän lippuDTO listan

        for (Lippu lippu : liput){  //Looppaa myytyjen lippujen läpi
        Long lippuId = lippu.getLippuId(); //Hakee lippujen id:t
        LippuDTO lippudto = lippuService.getLipputiedot(lippuId); //Luo LippuDTO -objektin LippuServicen avulla
        lippudtot.add(lippudto); //Lisää LippuDTO objektin listaan
        }

        Tyontekija tyontekija = myynti.getTyontekija();
        Long tyontekijaId = tyontekija.getTyontekijaId();  //Hakee myynnin työntekijän id:n

        //Luo uuden MyyntiDTO:n, johon vie haetun myynnin tiedot sekä listatut lippuDTO:t
        MyyntiDTO myyntiDTO = new MyyntiDTO();
        myyntiDTO.setMyyntiId(myynti.getMyyntiId());
        myyntiDTO.setMyyntiaika(myynti.getMyyntiaika());
        myyntiDTO.setTyontekijaId(tyontekijaId);
        myyntiDTO.setEmail(myynti.getEmail());
        myyntiDTO.setLiput(lippudtot);

        return myyntiDTO;
    }


    public List<MyyntiDTO> getAllMyynnit() {
        Iterable<Myynti> iterableMyynnit = myyntiRepository.findAll();  //Hakee kaikki myyntitapahtumat
        List<Myynti> myynnit = new ArrayList<Myynti>(); // Luo Listamuuttujan myynneille

        for (Myynti myynti : iterableMyynnit) { // Looppaa iterable myyntien läpi ja lisää ne listaan
            myynnit.add(myynti); // Add to the list
        }

        return myynnit.stream().map(myynti -> {
            MyyntiDTO myyntiDTO = getMyyntiById(myynti.getMyyntiId()); //Muokkaa myynti entityt MyyntiDTO -objekteiksi
            return myyntiDTO; 
        }).collect(Collectors.toList()); //Kerää myyntiDTO -objektit listaan
    }
}
