package com.if7100.repository.relacionesRepository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.if7100.entity.Modalidad;
import com.if7100.entity.relacionesEntity.ModalidadesPaises;

@Repository
public interface ModalidadesPaisesRepository  extends JpaRepository<ModalidadesPaises, Integer>  {
    
        List<ModalidadesPaises> findByModalidad(@Param("modalidad") Modalidad modalidad);

}
