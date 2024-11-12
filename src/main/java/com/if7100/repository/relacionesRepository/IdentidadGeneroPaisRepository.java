package com.if7100.repository.relacionesRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.if7100.entity.relacionesEntity.IdentidadGeneroPais;

@Repository
public interface IdentidadGeneroPaisRepository extends JpaRepository<IdentidadGeneroPais, Long> {

    
}
