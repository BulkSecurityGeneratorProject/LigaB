package com.polguillen.app.repository;

import com.polguillen.app.domain.Temporada;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Temporada entity.
 */
public interface TemporadaRepository extends JpaRepository<Temporada,Long> {

    @Query("select distinct temporada from Temporada temporada left join fetch temporada.equiposActualess")
    List<Temporada> findAllWithEagerRelationships();

    @Query("select temporada from Temporada temporada left join fetch temporada.equiposActualess where temporada.id =:id")
    Temporada findOneWithEagerRelationships(@Param("id") Long id);

}
