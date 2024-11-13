package com.if7100.repository.relacionesRepository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.if7100.entity.IdentidadGenero;
import com.if7100.entity.relacionesEntity.IdentidadGeneroPais;

@Repository
public interface IdentidadGeneroPaisRepository extends JpaRepository<IdentidadGeneroPais, Long> {
    
    List<IdentidadGeneroPais> findByIdentidadGenero(@Param("identidadGenero") IdentidadGenero identidadGenero);
    
   
}
