// CompetenciaRepository.java
package com.system.proyect.repositories;

import com.system.proyect.models.Competencia;
import com.system.proyect.models.EstadoCompetencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CompetenciaRepository extends JpaRepository<Competencia, Long> {

    List<Competencia> findByEstadoNot(EstadoCompetencia estado);

    List<Competencia> findByEstado(EstadoCompetencia estado);

    long countByEstado(EstadoCompetencia estado);
}