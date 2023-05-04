package com.if7100.repository;

import com.if7100.entity.Hecho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HechoRepository extends JpaRepository<Hecho, Integer> {

    Hecho findByCIPais(Integer CIPais);

    Hecho findByCITipoVictima(Integer CITipoVictima);

    Hecho findByCITipoRelacion(Integer CITipoRelacion);

    Hecho findByCIModalidad(Integer CIModalidad);

}
