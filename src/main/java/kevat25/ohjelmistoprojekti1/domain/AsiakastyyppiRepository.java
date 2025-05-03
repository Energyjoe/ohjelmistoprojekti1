package kevat25.ohjelmistoprojekti1.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@RepositoryRestResource(collectionResourceRel = "asiakastyypit", path = "asiakastyypit")
public interface AsiakastyyppiRepository extends CrudRepository<Asiakastyyppi, Long> {

    Asiakastyyppi findByAsiakastyyppi(String asiakastyyppi);

}