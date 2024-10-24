package com.if7100.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.if7100.entity.TipoRelacionFamiliar;

@Repository
public interface TipoRelacionFamiliarRepository extends JpaRepository<TipoRelacionFamiliar, Integer>{

}
