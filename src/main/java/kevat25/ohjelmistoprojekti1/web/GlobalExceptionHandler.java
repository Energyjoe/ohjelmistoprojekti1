/*
JWTFilter-luokan lisäämisen jälkeen osa kontrollerien virheilmoituksista jäi
saapumatta ja tilalla näkyi 403 Forbidden-virheilmoitus, ikäänkuin käyttäjä ei
olisi autentikoitu. Tämä luokka käsittelee tietyt kaikkien kontrollerien poikkeukset
ja palauttaa niistä virheilmoitukset.

@RestControllerAdvice: Tämä luokka käsittelee virheitä, jotka tapahtuvat
controller-luokissa. Kun controllerissa tapahtuu virhe, tämä luokka
käsittelee sen ja palauttaa virheilmoituksen asiakkaalle. Kaikki tällä merkitys
metodit käyttäytyvät kuin niillä olisi @ResponseBody-annotaatio.

Mikäli metodi palauttaa ResponseEntity-olion, se tarkoittaa, että metodi
palauttaa HTTP-vastauksen, joka sisältää statuskoodin ja mahdollisesti
dataa. Spring sarjoittaa sen oletuksena JSON-muotoon.

@ExceptionHandler: Tämä annotaatio kertoo, että metodi käsittelee
tietyn tyyppisiä poikkeuksia. Esim. MethodArgumentNotValidException
 */

package kevat25.ohjelmistoprojekti1.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Tämä metodi käsittelee kaikki virheet, jotka liittyvät
    // @Valid-annotaatioon.
    // "Exception to be thrown when validation on an argument annotated with {@code
    // @Valid} fails."
    // Esim. tapahtumaController-luokassa:
    // @PutMapping("/{tapahtumaId}")
    // public ResponseEntity<Object> muokkaaTapahtumaa(@Valid @PathVariable Long
    // tapahtumaId,
    // @Valid @RequestBody Tapahtuma uusiTapahtuma) {
    // ^^^^^^
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });
        return errors;
    }

    // Tämä metodi käsittelee kaikki ResponseStatusException-virheet. Esim.
    // TyontekijaService-luokassa:
    // throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Paikkakunta ei
    // täsmää postinumeroon");
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, String>> handleResponseStatusException(ResponseStatusException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("Virhe", ex.getReason());
        // Palautetaan sama HTTP‑status kuin poikkeuksessa (esim. UNAUTHORIZED tai
        // BAD_REQUEST)
        return ResponseEntity
                .status(ex.getStatusCode())
                .body(error);
    }
}
