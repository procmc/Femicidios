package com.if7100.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.if7100.entity.Paises;

@Repository
public interface PaisesRepository extends JpaRepository<Paises,Integer>  {

    Paises findById(String string);
    Paises findByISO2(String iso2);

}
