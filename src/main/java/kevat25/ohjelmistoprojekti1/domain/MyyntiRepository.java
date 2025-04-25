package kevat25.ohjelmistoprojekti1.domain;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "myynnit", path = "myynnit")
public interface MyyntiRepository extends CrudRepository<Myynti, Long> {

 @Query("SELECT m FROM Myynti m WHERE DATE(m.myyntiaika) = :date")
List<Myynti> findMyyntiByDay(@Param("date") LocalDate date);

}