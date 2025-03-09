package kevat25.ohjelmistoprojekti1.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@RepositoryRestResource(collectionResourceRel = "liput", path = "liput")
public interface LippuRepository extends CrudRepository<Lippu, Long> {
    List<Lippu> findByMyyntiId(Long myyntiId);
    }


