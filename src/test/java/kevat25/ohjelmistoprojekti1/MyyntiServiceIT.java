package kevat25.ohjelmistoprojekti1;

/*
 * Tämä testi tarkistaa, että API palauttaa /myynnit/-polulle tehdyn GET-pyynnön
 * yhteydessä listan myynneistä,
 * että vastaus on JSON-muodossa ja että autentikointi toimii JWT-tunnisteella.
 */

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import kevat25.ohjelmistoprojekti1.service.JwtService;
import kevat25.ohjelmistoprojekti1.service.MyyntiService;

/** Integraatio testi myyntiServicelle {@link MyyntiService} */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("dev")
public class MyyntiServiceIT {

    private final MockMvc mockMvc;

    @Autowired
    private JwtService jwtService;

    @Autowired
    public MyyntiServiceIT(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    public void testGetAllMyynnitPalauttaaKaikkiMyynnit() throws Exception {
        String jwtToken = TestUtil.generateTestToken(jwtService);

        mockMvc.perform(get("/myynnit/")
                .header("Authorization", jwtToken))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$").isArray());
    }
}
