package kevat25.ohjelmistoprojekti1.service;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

/*
 *  Miten JwtService liittyy kirjautumisprosessiin?
1️⃣ Käyttäjä lähettää sähköpostin ja salasanan.
2️⃣ Spring Security tarkistaa, että sähköposti löytyy ja salasana täsmää (BCrypt).
3️⃣ Jos kirjautuminen onnistuu, JwtService luo JWT-tokenin sähköpostilla.
4️⃣ Käyttäjä käyttää tokenia API-kutsujen autentikointiin.
5️⃣ JwtService tarkistaa tokenin ja sähköpostin, kun käyttäjä tekee pyyntöjä.
 */

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secretKey; // salainen avain, jota käytetään tokenin allekirjoittamiseen ja varmistetaan
                              // ettei tokenia ole muokattu

    @Value("${jwt.expiration}")
    private long jwrExpirationsMs; // tokenin voimassaoloaika millisekunteina

    private Key getSingingKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes()); // luo ja palauttaa salausavaimen jota käytetään tokenin
                                                         // allekirjoittamiseen (HMAC SHA- avain)
    }

    // 1. Tokenin luonti käyttäjälle -> tiedot jotka kulkevat tokenin mukana!
    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email) // sähköpostiosoite tokeniin
                .setIssuedAt(new Date()) // luontiaika
                .setExpiration(new Date(System.currentTimeMillis() + jwrExpirationsMs)) // vanhentumisaika
                .signWith(getSingingKey(), SignatureAlgorithm.HS256) // allekirjoitus HMAC SHA256 -algoritmilla
                .compact(); // rakennetaan token merkkijonoksi
    }

    // 2. Tokenin validointi -> tarkistaa onko token kelvollinen
    public boolean validateToken(String token, String email) {
        final String extractedEmail = extractEmail(token);
        return (extractedEmail.equals(email) && !isTokenExpired(token));
    }

    // 3. Käyttäjänimen purkaminen tokenista
    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // 4. Tokenin vanhenemisen tarkastus
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // 5. Vanhenemisajan haku tokenista
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration); // Claims hakee vanhenemisajan
    }

    // 6. Yleinen metodi tietojen hakuun tokenista
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // 7. Purkaa kaikki tiedot tokenista -> palauttaa tokenin sisältämät tiedot
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSingingKey()) // Käytetään samaa allekirjoitusavainta
                .build()
                .parseClaimsJws(token) // parsitaan JWT
                .getBody(); // palautetaan tokenin sisältö
    }
}
