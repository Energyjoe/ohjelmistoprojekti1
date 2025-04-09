package kevat25.ohjelmistoprojekti1.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.lang.NonNull;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


// https://www.youtube.com/watch?v=ZhtF4i-iB1A #38 Spring Security | Validating JWT Token

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, 
                                    @NonNull HttpServletResponse response, 
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        // Hae Authorization-otsake pyynnöstä
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String email = null;

        // Tarkista, onko otsake olemassa ja alkaako se "Bearer "-etuliitteellä
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            // Poista "Bearer "-etuliite ja tallenna tunniste
            token = authHeader.substring(7);
            // Hae sähköpostiosoite (tai käyttäjänimi) tunnisteesta JwtService-luokan avulla
            email = jwtService.extractEmail(token);
        }

        // Jos sähköpostiosoite on haettu ja autentikointia ei ole vielä asetettu
        // SecurityContextiin
        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Validoi tunniste ja varmista, että se vastaa sähköpostiosoitetta
            if (jwtService.validateToken(token, email)) {
                // Luo autentikointitunniste käyttäjälle
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(email, null,
                        null);
                // Aseta yksityiskohtia autentikointitunnisteeseen (kerää pyynnöstä tietoja,
                // jotka voivat olla hyödyllisiä autentikoinnin tai lokituksen kannalta)
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                // Aseta autentikointi SecurityContextiin
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // Jatka seuraavaan suodattimeen ketjussa
        filterChain.doFilter(request, response);
    }

}
