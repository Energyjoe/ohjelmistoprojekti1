package kevat25.ohjelmistoprojekti1.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SalasanaTarkistusService {

    private final BCryptPasswordEncoder passwordEncoder;

    
    public SalasanaTarkistusService() {
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public boolean tarkistaSalasana(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
