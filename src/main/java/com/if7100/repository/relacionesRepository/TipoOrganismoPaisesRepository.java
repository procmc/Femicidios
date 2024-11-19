package com.if7100.repository.relacionesRepository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.if7100.entity.TipoOrganismo;
import com.if7100.entity.relacionesEntity.TipoOrganismoPaises;

@Repository
public interface TipoOrganismoPaisesRepository extends JpaRepository<TipoOrganismoPaises, Integer>{

        List<TipoOrganismoPaises> findByTipoOrganismo(
            @Param("tipoOrganismo") TipoOrganismo tipoOrganismo);
}
