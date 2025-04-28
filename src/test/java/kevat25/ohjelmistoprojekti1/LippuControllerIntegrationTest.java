package kevat25.ohjelmistoprojekti1;

import kevat25.ohjelmistoprojekti1.domain.*;
import kevat25.ohjelmistoprojekti1.service.JwtService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.http.MediaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class LippuControllerIntegrationTest {
    
@Autowired
private TestRestTemplate restTemplate;

@Autowired
private LippuRepository lippuRepository;

@Autowired
private MyyntiRepository myyntiRepository;

@Autowired
private TapahtumalippuRepository tapahtumalippuRepository;

@Autowired
private TapahtumaRepository tapahtumaRepository;

@Autowired
private TyontekijaRepository tyontekijaRepository;

@Autowired
private TapahtumapaikkaRepository tapahtumapaikkaRepository;

@Autowired
private PostinumeroRepository postinumeroRepository;

@Autowired
private AsiakastyyppiRepository asiakastyyppiRepository;

@Autowired
private JwtService jwtService;

@Autowired
private WebApplicationContext webApplicationContext;

private MockMvc mockMvc;


@BeforeEach
void setUp() {
lippuRepository.deleteAll();
myyntiRepository.deleteAll();
tapahtumalippuRepository.deleteAll();
mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
}



@Test
void testPostLippu() throws Exception {


// Generoidaan jwt-token käyttäjälle
String jwtToken = TestUtil.generateTestToken(jwtService);
    
// Autentikointi
HttpHeaders headers = new HttpHeaders();
headers.set("Authorization", jwtToken);
    

    // Luodaan lippua varten tarvittavat entiteetit
    Tyontekija tyontekija = new Tyontekija();
    tyontekija.setEtunimi("Testi");
    tyontekija.setSukunimi("Testityontekija");
    tyontekija.setEmail("testi@oprojekti1.com");
    tyontekija.setPuhnro("0501234567");
    tyontekija.setBcrypthash("salasana123");
    tyontekija.setKatuosoite("Testikatu 1");
    tyontekijaRepository.save(tyontekija);

    Myynti myynti = new Myynti();
    myynti.setTyontekija(tyontekija);
    myyntiRepository.save(myynti);

    Tapahtumapaikka tapahtumapaikka = new Tapahtumapaikka();
    tapahtumapaikka.setTapahtumapaikka("Testipaikka");
    tapahtumapaikka.setKatuosoite("Testikatu 2");
    tapahtumapaikka.setKapasiteetti(500);
    tapahtumapaikkaRepository.save(tapahtumapaikka);

    Tapahtuma tapahtuma = new Tapahtuma();
    tapahtuma.setTapahtumaNimi("Testitapahtuma");
    tapahtuma.setKapasiteetti(100);
    tapahtuma.setTapahtumapaikka(tapahtumapaikka);
    tapahtuma.setAloitusaika(java.time.LocalDateTime.now().plusDays(1)); // Set a valid future start time
    tapahtuma.setLopetusaika(java.time.LocalDateTime.now().plusDays(2)); // Set a valid future end time
    tapahtumaRepository.save(tapahtuma);

    Asiakastyyppi asiakastyyppi = new Asiakastyyppi();
    asiakastyyppi.setAsiakastyyppi("Testi");
    asiakastyyppiRepository.save(asiakastyyppi); // Save the Asiakastyyppi entity

    Tapahtumalippu tapahtumalippu = new Tapahtumalippu();
    tapahtumalippu.setTapahtuma(tapahtuma);
    tapahtumalippu.setHinta(new java.math.BigDecimal("50.0")); // Set a valid price
    tapahtumalippu.setAsiakastyyppi(asiakastyyppi); // Set a valid customer type
    tapahtumalippuRepository.save(tapahtumalippu);

    // Luodaan DTO lippua varten
    LippuPostDTO request = new LippuPostDTO();
    request.setMyyntiId(myynti.getMyyntiId());
    request.setTapahtumalippuId(tapahtumalippu.getTapahtumalippuId());

    HttpEntity<LippuPostDTO> entity = new HttpEntity<>(request, headers);

    // Muunnetaan pyyntö JSON-muotoon
    String requestJson = new ObjectMapper().writeValueAsString(request);


    
// Lähetetään POST-pyyntö ja tarkistetaan vastaus
    mockMvc.perform(post("/liput/")
    .header("Authorization", jwtToken)
    .contentType(MediaType.APPLICATION_JSON)
    .content(requestJson))
    .andDo(print())
    .andExpect(status().isCreated())
    .andExpect(jsonPath("$.tapahtumalippu.tapahtumalippuId").value(tapahtumalippu.getTapahtumalippuId()))
    .andExpect(jsonPath("$.myynti.myyntiId").value(myynti.getMyyntiId()))
    .andExpect(jsonPath("$.tarkistuskoodi").isNotEmpty())
    .andExpect(jsonPath("$.tarkistettu").value(false));

}
}

