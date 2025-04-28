package kevat25.ohjelmistoprojekti1.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@RepositoryRestResource(collectionResourceRel = "liput", path = "liput")
public interface LippuRepository extends CrudRepository<Lippu, Long> {
    List<Lippu> findByMyyntiMyyntiId(Long myyntiId);
    List<Lippu> findByLippuId(Long lippuId);

    Optional<Lippu> findByTarkistuskoodi(String tarkistuskoodi);


    //Laskee tapahtuma-id:n perusteella kuinka monta lippua on myyty kyseiseen tapahtumaan 
    @Query("SELECT COUNT(l) FROM Lippu l JOIN l.tapahtumalippu tl WHERE tl.tapahtuma.tapahtumaId = :tapahtumaId")
    long countByTapahtumaId(@Param("tapahtumaId") Long tapahtumaId);

    // Tämä on JPQL-kysely (Java Persistence Query Language), joka tarkistaa, onko
    // tietty tarkistuskoodi jo olemassa tietyn tapahtuma-ID:n alla.
    @Query("SELECT COUNT(l) > 0 FROM Lippu l WHERE l.tapahtumalippu.tapahtuma.id = :tapahtumaId AND l.tarkistuskoodi = :tarkistuskoodi")
    boolean existsByTapahtumaAndTarkistuskoodi(Long tapahtumaId, String tarkistuskoodi);

    @Query("SELECT l FROM Lippu l WHERE l.tapahtumalippu.tapahtuma.tapahtumaId = :tapahtumaId")
    List<Lippu> findByTapahtumaId(@Param("tapahtumaId") Long tapahtumaId);
}