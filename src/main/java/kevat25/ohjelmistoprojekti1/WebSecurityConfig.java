package kevat25.ohjelmistoprojekti1;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    
    @Bean
    public SecurityFilterChain SecurityFilterChain(HttpSecurity http) throws Exception {
        http
        .csrf().disable()
        .authorizeHttpRequests((requests) -> requests
        .requestMatchers("/**").permitAll()
        .anyRequest().authenticated());
        return http.build();
    } 

}