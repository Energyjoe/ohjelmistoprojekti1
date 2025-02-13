// filepath: src/test/java/kevat25/ohjelmistoprojekti1/PostinumerotRepositoryTest.java
package kevat25.ohjelmistoprojekti1;

import kevat25.ohjelmistoprojekti1.domain.Postinumerot;
import kevat25.ohjelmistoprojekti1.domain.PostinumerotRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class PostinumerotRepositoryTest {

    @Autowired
    private PostinumerotRepository postinumerotRepository;

    @Test
    public void testCreatePostinumerot() {
        Postinumerot postinumero = new Postinumerot("12345", "Testikaupunki");
        postinumerotRepository.save(postinumero);
        assertThat(postinumerotRepository.findByPostinumero("12345")).isNotNull();
    }

    @Test
    public void testFindByPostinumero() {
        Postinumerot postinumero = new Postinumerot("54321", "Toinenkaupunki");
        postinumerotRepository.save(postinumero);
        Postinumerot foundPostinumero = postinumerotRepository.findByPostinumero("54321");
        assertThat(foundPostinumero.getPaikkakunta()).isEqualTo("Toinenkaupunki");
    }
}