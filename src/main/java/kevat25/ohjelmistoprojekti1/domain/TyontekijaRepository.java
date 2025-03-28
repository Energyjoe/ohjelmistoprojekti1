package kevat25.ohjelmistoprojekti1.domain;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "tyontekijat", path = "tyontekijat")
public interface TyontekijaRepository extends CrudRepository<Tyontekija, Long> {

    Optional<Tyontekija> findByEmail(String email);
}