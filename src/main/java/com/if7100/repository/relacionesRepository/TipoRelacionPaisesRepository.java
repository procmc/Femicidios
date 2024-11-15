package com.if7100.repository.relacionesRepository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.if7100.entity.TipoRelacion;
import com.if7100.entity.relacionesEntity.TipoRelacionPaises;

@Repository
public interface TipoRelacionPaisesRepository extends JpaRepository<TipoRelacionPaises, Integer>{
    
        List<TipoRelacionPaises> findByTipoRelacion(
            @Param("TipoRelacion") TipoRelacion TipoRelacion);

}
