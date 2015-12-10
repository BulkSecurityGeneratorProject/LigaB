package com.polguillen.app.repository;

import com.polguillen.app.domain.Estadio;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Estadio entity.
 */
public interface EstadioRepository extends JpaRepository<Estadio,Long> {

}
