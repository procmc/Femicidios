package com.if7100.repository.relacionesRepository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.if7100.entity.TipoRelacionFamiliar;
import com.if7100.entity.relacionesEntity.TipoRelacionFamiliarPaises;

@Repository
public interface TipoRelacionFamiliarPaisesRepository extends JpaRepository<TipoRelacionFamiliarPaises, Integer>{
    
    List<TipoRelacionFamiliarPaises> findByTipoRelacionFamiliar(
            @Param("tipoRelacionFamiliar") TipoRelacionFamiliar tipoRelacionFamiliar);

}
