package kevat25.ohjelmistoprojekti1.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "myynnit", path = "myynnit")
public interface MyyntiRepository extends CrudRepository<Myynti, Long> {

    @Query("SELECT m FROM Myynti m WHERE FUNCTION('DATE', m.myyntiaika) = :date")
    List<Myynti> findMyyntiByDay(@Param("date") LocalDate date);

    @Query("SELECT m FROM Myynti m WHERE m.myyntiaika BETWEEN :startDate AND :endDate")
    List<Myynti> findMyyntiByDateRange(@Param("startDate") LocalDateTime startDate, 
                                        @Param("endDate") LocalDateTime endDate);

    @Query("SELECT m FROM Myynti m WHERE m.tyontekija.id = :tyontekijaId")
    List<Myynti> findMyyntiByTyontekija(@Param("tyontekijaId") Long tyontekijaId);

    @Query("SELECT m FROM Myynti m WHERE m.tyontekija.id = :tyontekijaId AND m.myyntiaika BETWEEN :startDate AND :endDate")
    List<Myynti> findMyyntiByTyontekijaAndDateRange(@Param("tyontekijaId") Long tyontekijaId,
                                                    @Param("startDate") LocalDateTime startDate,
                                                    @Param("endDate") LocalDateTime endDate);


}