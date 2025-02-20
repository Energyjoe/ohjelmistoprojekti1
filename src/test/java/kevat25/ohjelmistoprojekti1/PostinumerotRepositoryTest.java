// filepath: src/test/java/kevat25/ohjelmistoprojekti1/PostinumerotRepositoryTest.java
package kevat25.ohjelmistoprojekti1;

import kevat25.ohjelmistoprojekti1.domain.Postinumero;
import kevat25.ohjelmistoprojekti1.domain.PostinumeroRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class PostinumerotRepositoryTest {

    @Autowired
    private PostinumeroRepository postinumerotRepository;

    @Test
    public void testCreatePostinumerot() {
        Postinumero postinumero = new Postinumero("12345", "Testikaupunki");
        postinumerotRepository.save(postinumero);
        assertThat(postinumerotRepository.findByPostinumero("12345")).isNotNull();
    }

    @Test
    public void testFindByPostinumero() {
        Postinumero postinumero = new Postinumero("54321", "Toinenkaupunki");
        postinumerotRepository.save(postinumero);
        Postinumero foundPostinumero = postinumerotRepository.findByPostinumero("54321");
        assertThat(foundPostinumero.getPaikkakunta()).isEqualTo("Toinenkaupunki");
    }
}