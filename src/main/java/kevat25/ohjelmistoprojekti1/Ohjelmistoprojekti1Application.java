package kevat25.ohjelmistoprojekti1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "kevat25.ohjelmistoprojekti1") // scanBasePackages varmistaa, että kaikki
																			// luokat skannataan oikein t. JJ
public class Ohjelmistoprojekti1Application {

	public static void main(String[] args) {
		SpringApplication.run(Ohjelmistoprojekti1Application.class, args);
	}

}
