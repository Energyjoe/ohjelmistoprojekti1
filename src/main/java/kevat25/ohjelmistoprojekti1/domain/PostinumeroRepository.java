package kevat25.ohjelmistoprojekti1.domain;

import java.util.function.IntPredicate;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "postinumerot", path = "postinumerot")
public interface PostinumeroRepository extends CrudRepository<Postinumero, String> {
    Postinumero findByPostinumero(Postinumero postinumero);

    Postinumero findByPostinumero(String string);

}