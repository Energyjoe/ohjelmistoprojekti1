package kevat25.ohjelmistoprojekti1.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TyontekijaRepository extends CrudRepository<Tyontekija, Long> {

}