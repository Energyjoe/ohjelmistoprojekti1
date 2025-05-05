/*package kevat25.ohjelmistoprojekti1.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class FallbackController {

    @RequestMapping(value = "/**")
    public ResponseEntity<Map<String, String>> handleUnknownRequest(HttpServletRequest request) {
        Map<String, String> error = new HashMap<>();
        error.put("Virhe", "Reittiä ei löytynyt: " + request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
}*/