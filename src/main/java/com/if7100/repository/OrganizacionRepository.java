package com.if7100.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.if7100.entity.Hecho;
import com.if7100.entity.Organizacion;

@Repository
public interface OrganizacionRepository extends JpaRepository<Organizacion, Integer>{
    
    List<Organizacion> findByCodigoPais(Integer codigoPais);

}
