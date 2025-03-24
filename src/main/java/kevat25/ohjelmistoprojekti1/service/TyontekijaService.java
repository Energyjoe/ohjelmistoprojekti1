package kevat25.ohjelmistoprojekti1.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import kevat25.ohjelmistoprojekti1.domain.Postinumero;
import kevat25.ohjelmistoprojekti1.domain.PostinumeroRepository;
import kevat25.ohjelmistoprojekti1.domain.SalasanaUpdateDTO;
import kevat25.ohjelmistoprojekti1.domain.Tyontekija;
import kevat25.ohjelmistoprojekti1.domain.TyontekijaCreateDTO;
import kevat25.ohjelmistoprojekti1.domain.TyontekijaRepository;
import kevat25.ohjelmistoprojekti1.domain.TyontekijaResponseDTO;
import kevat25.ohjelmistoprojekti1.domain.TyontekijaUpdateDTO;

@Service
public class TyontekijaService {

    private final TyontekijaRepository tyontekijaRepository;
    private final PostinumeroRepository postinumeroRepository;
    private final PasswordEncoder passwordEncoder;

    public TyontekijaService(TyontekijaRepository tyontekijaRepository,
            PostinumeroRepository postinumeroRepository, PasswordEncoder passwordEncoder) {
        this.tyontekijaRepository = tyontekijaRepository;
        this.postinumeroRepository = postinumeroRepository;
        this.passwordEncoder = passwordEncoder;
    }

    //Haetaan yhden työntekijän tiedot
    public TyontekijaResponseDTO getTyontekijaById(Long tyontekijaId) {
        Tyontekija tyontekija = tyontekijaRepository.findById(tyontekijaId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Työntekijää ei löydy id:llä: " + tyontekijaId));

        return new TyontekijaResponseDTO(tyontekija);
    }

    //Haetaan kaikki työntekijät
    public List<TyontekijaResponseDTO> getAllTyontekijat() {
        Iterable<Tyontekija> iterableTyontekijat = tyontekijaRepository.findAll();  //Hakee kaikki myyntitapahtumat
        List<Tyontekija> tyontekijat = new ArrayList<Tyontekija>(); // Luo Listamuuttujan myynneille

        for (Tyontekija tyontekija : iterableTyontekijat) { // Looppaa iterable myyntien läpi ja lisää ne listaan
            tyontekijat.add(tyontekija); // Add to the list
        }

        return tyontekijat.stream().map(tyontekija -> {
            TyontekijaResponseDTO responseDto = getTyontekijaById(tyontekija.getTyontekijaId()); //Muokkaa tyontekija entityt TyontekijaResponseDTO -objekteiksi
            return responseDto; 
        }).collect(Collectors.toList()); //Kerää responseDTO -objektit listaan
    }

    //Luodaan uusi työntekijä
    public TyontekijaResponseDTO createTyontekija(TyontekijaCreateDTO createDto) {

        Tyontekija tyontekija = new Tyontekija();
        tyontekija.setEtunimi(createDto.getEtunimi());
        tyontekija.setSukunimi(createDto.getSukunimi());
        tyontekija.setEmail(createDto.getEmail());
        tyontekija.setPuhnro(createDto.getPuhnro());
        tyontekija.setKatuosoite(createDto.getKatuosoite());

        // Hashataan salasana ennen tallennusta
        tyontekija.setBcrypthash(passwordEncoder.encode(createDto.getBcrypthash()));

        // Haetaan postinumero tietokannasta ja lisätään käyttäjälle
        Postinumero postinumero = postinumeroRepository.findByPostinumero(createDto.getPostinumero().getPostinumero());

        if (postinumero == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Postinumeroa ei löydy");
        }

        if (!postinumero.getPaikkakunta().equals(createDto.getPostinumero().getPaikkakunta())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Paikkakunta ei täsmää postinumeroon");
        }

        postinumero.setPaikkakunta(createDto.getPostinumero().getPaikkakunta());

        tyontekija.setPostinumero(postinumero);
        Tyontekija savedTyontekija = tyontekijaRepository.save(tyontekija);

        return new TyontekijaResponseDTO(savedTyontekija);
    }

    // Päivitetään kaikki muut työntekijän tiedot paitsi salasana.
    public TyontekijaResponseDTO updateTyontekija(Long tyontekijaId, TyontekijaUpdateDTO updateDto) {

        Tyontekija tyontekija = tyontekijaRepository.findById(tyontekijaId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Työntekijää ei löydy"));

        // Päivitetään vain ne kentät, jotka eivät ole null
        if (updateDto.getEtunimi() != null) {
            tyontekija.setEtunimi(updateDto.getEtunimi());
        }
        if (updateDto.getSukunimi() != null) {
            tyontekija.setSukunimi(updateDto.getSukunimi());
        }
        if (updateDto.getEmail() != null) {
            tyontekija.setEmail(updateDto.getEmail());
        }
        if (updateDto.getPuhnro() != null) {
            tyontekija.setPuhnro(updateDto.getPuhnro());
        }
        if (updateDto.getKatuosoite() != null) {
            tyontekija.setKatuosoite(updateDto.getKatuosoite());
        }

        // Tarkistetaan postinumero, jos se on annettu
        if (updateDto.getPostinumero() != null) {
            Postinumero postinumero = postinumeroRepository
                    .findByPostinumero(updateDto.getPostinumero().getPostinumero());

            if (postinumero == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Postinumeroa ei löydy");
            }

            // Päivitetään paikkakunta, jos se on annettu
            if (updateDto.getPostinumero().getPaikkakunta() != null) {
                postinumero.setPaikkakunta(updateDto.getPostinumero().getPaikkakunta());
            }

            tyontekija.setPostinumero(postinumero);
        }

        // Tallennetaan päivitetty työntekijä
        Tyontekija updatedTyontekija = tyontekijaRepository.save(tyontekija);

        return new TyontekijaResponseDTO(updatedTyontekija);
    }

    // Päivitetään työntekijän salasana
    public void updateSalasana(Long tyontekijaId, SalasanaUpdateDTO salasanaDto) {
        Tyontekija tyontekija = tyontekijaRepository.findById(tyontekijaId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Työntekijää ei löydy"));

        // Tarkistetaan, täsmääkö vanha salasana
        if (!passwordEncoder.matches(salasanaDto.getVanhaSalasana(), tyontekija.getBcrypthash())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Väärä vanha salasana");
        }

        // Päivitetään salasana
        tyontekija.setBcrypthash(passwordEncoder.encode(salasanaDto.getUusiSalasana()));
        tyontekijaRepository.save(tyontekija);
    }

}
