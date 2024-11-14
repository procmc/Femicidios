package com.if7100.repository.relacionesRepository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.if7100.entity.OrientacionSexual;
import com.if7100.entity.relacionesEntity.OrientacionesSexualesPaises;

@Repository
public interface OrientacionesSexualesPaisesRepository extends JpaRepository<OrientacionesSexualesPaises, Integer> {

    List<OrientacionesSexualesPaises> findByOrientacionSexual(@Param("orientacionSexual") OrientacionSexual orientacionSexual);

}
