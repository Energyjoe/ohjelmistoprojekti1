package kevat25.ohjelmistoprojekti1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import kevat25.ohjelmistoprojekti1.service.JwtFilter;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

// https://www.youtube.com/watch?v=ZhtF4i-iB1A #38 Spring Security | Validating JWT Token

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain SecurityFilterChain(HttpSecurity http) throws Exception {
        http
                // Poista CSRF-suojaus käytöstä (ei tarpeen JWT-pohjaisessa autentikoinnissa)
                .csrf(customizer -> customizer.disable())
                // Määritä pääsynhallinta
                .authorizeHttpRequests((requests) -> requests
                        // Salli pääsy kirjautumispolkuihin ilman autentikointia
                        .requestMatchers("/**").permitAll()
                        .anyRequest().authenticated()) // Vaadi autentikointi muilta pyynnöiltä
                // Lisää mukautettu JWT-suodatin ennen käyttäjätunnus-salasana-suodatinta
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}