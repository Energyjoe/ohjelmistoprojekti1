package kevat25.ohjelmistoprojekti1.web;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public ResponseEntity<Map<String, String>> handleError(HttpServletRequest request) {
        Object status = request.getAttribute("jakarta.servlet.error.status_code");

        if (status != null && Integer.parseInt(status.toString()) == HttpStatus.NOT_FOUND.value()) {
            Map<String, String> error = new HashMap<>();
            error.put("Virhe", "Reittiä ei löytynyt: " + request.getRequestURI());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }

        // Muu virhe (500 jne.)
        Map<String, String> genericError = new HashMap<>();
        genericError.put("Virhe", "Tapahtui odottamaton virhe");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(genericError);
    }
}

