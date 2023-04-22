package com.if7100.repository;

import com.if7100.entity.Hecho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HechoRepository extends JpaRepository<Hecho, Integer> {

    Hecho findByCVLugar(Integer CVLugar);

    Hecho findByCVTipoVictima(Integer CVTipoVictima);

    Hecho findByCVTipoRelacion(Integer CVTipoRelacion);

    Hecho findByCVModalidad(Integer CVModalidad);

}
