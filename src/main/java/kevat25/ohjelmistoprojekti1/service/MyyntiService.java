package kevat25.ohjelmistoprojekti1.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import jakarta.persistence.EntityNotFoundException;
import kevat25.ohjelmistoprojekti1.domain.*;

@Service
public class MyyntiService {

    @Autowired
    private MyyntiRepository myyntiRepository;

    @Autowired
    private LippuRepository lippuRepository;

    @Autowired
    private LippuService lippuService; // Service, joka täyttää LippuDTO -objektin datalla

    @Autowired
    private TyontekijaRepository tyontekijaRepository;

    //Tarkistaa, onko syötetty sähköposti kelvollinen
    private boolean isValidEmail(String email) {
        return email != null && email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    }

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

    //Muokkaa myyntitapahtumaa
    public Myynti muokkaaMyyntia(Long myyntiId, MyyntiDTO myyntiDTO) {
        Optional<Myynti> myyntiOpt = myyntiRepository.findById(myyntiId);
        if (myyntiOpt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        Myynti myynti = myyntiOpt.get();

        if (!isValidEmail(myyntiDTO.getEmail())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        myynti.setEmail(myyntiDTO.getEmail());

        if (myyntiDTO.getTyontekijaId() != null) {
            Optional<Tyontekija> tyontekijaOpt = tyontekijaRepository.findById(myyntiDTO.getTyontekijaId());
            if (tyontekijaOpt.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
            myynti.setTyontekija(tyontekijaOpt.get());
        }

        return myyntiRepository.save(myynti);
    }
}
