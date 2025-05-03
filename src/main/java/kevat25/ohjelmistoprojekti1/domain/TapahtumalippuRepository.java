package kevat25.ohjelmistoprojekti1.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.stereotype.Repository;

@RepositoryRestResource(collectionResourceRel = "tapahtumaliput", path = "tapahtumaliput")
public interface TapahtumalippuRepository extends CrudRepository<Tapahtumalippu, Long> {

    List<Tapahtumalippu> findByTapahtuma(Tapahtuma tapahtuma);
    Tapahtumalippu findByTapahtumaAndAsiakastyyppi(Tapahtuma tapahtuma, Asiakastyyppi asiakastyyppi);

    boolean existsByTapahtumaAndAsiakastyyppi(Tapahtuma tapahtuma, Asiakastyyppi asiakastyyppi);

}