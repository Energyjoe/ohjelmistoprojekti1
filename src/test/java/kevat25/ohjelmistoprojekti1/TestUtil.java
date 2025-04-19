package kevat25.ohjelmistoprojekti1;

import kevat25.ohjelmistoprojekti1.domain.Tyontekija;
import kevat25.ohjelmistoprojekti1.service.JwtService;

import java.util.Set;

public class TestUtil {

    public static String generateTestToken(JwtService jwtService) {
        String testEmail = "testi@oprojekti1.com";
        return "Bearer " + jwtService.generateToken(testEmail);
    }
}
