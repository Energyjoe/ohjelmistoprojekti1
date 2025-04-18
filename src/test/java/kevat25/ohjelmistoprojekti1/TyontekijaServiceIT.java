package kevat25.ohjelmistoprojekti1;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("dev")
public class TyontekijaServiceIT {

    private final MockMvc mockMvc;

    @Autowired
    public TyontekijaServiceIT(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    public void testCreateTyontekija() throws Exception {
        String jwtToken = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbkBvcHJvamVrdGkxLmNvbSIsImlhdCI6MTc0NDk5NDQ4NiwiZXhwIjoxNzQ0OTk4MDg2fQ.BcvCsH5QmUtrbSvRoPqErKc7P81t-bOsGECPto4WiAg";

        String jsonPayload = """
                 {
                  "etunimi": "Testi",
                   "sukunimi": "Testaaja",
                   "email": "testi.testaaja@email.com",
                  "puhnro": "0501234567",
                  "katuosoite": "Testi 123",
                   "postinumero": {
                    "postinumero": "00100",
                     "paikkakunta": "Helsinki"
                   },
                "bcrypthash": "salasana123"
                 }
                 """;

        mockMvc.perform(post("/tyontekijat/")
                .header("Authorization", jwtToken)
                .contentType("application/json")
                .content(jsonPayload))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType("application/json"));
    }
}
