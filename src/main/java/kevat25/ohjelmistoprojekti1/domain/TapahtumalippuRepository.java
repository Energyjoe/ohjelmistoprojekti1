package kevat25.ohjelmistoprojekti1.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TapahtumalippuRepository extends CrudRepository<Tapahtumalippu, Long> {

}