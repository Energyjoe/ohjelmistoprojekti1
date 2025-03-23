package kevat25.ohjelmistoprojekti1.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "postinumerot", path = "postinumerot")
public interface PostinumeroRepository extends CrudRepository<Postinumero, String> {
    Postinumero findByPostinumero(String postinumero);

}