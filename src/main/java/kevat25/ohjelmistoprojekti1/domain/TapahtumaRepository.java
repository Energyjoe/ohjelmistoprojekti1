package kevat25.ohjelmistoprojekti1.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@RepositoryRestResource(collectionResourceRel = "tapahtumat", path = "tapahtumat")
public interface TapahtumaRepository extends CrudRepository<Tapahtuma, Long> {

}
