package kevat25.ohjelmistoprojekti1.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@RepositoryRestResource(collectionResourceRel = "tapahtumapaikat", path = "tapahtumapaikat")
public interface TapahtumapaikkaRepository extends CrudRepository<Tapahtumapaikka, Long> {

}