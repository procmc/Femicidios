package com.if7100.repository;

import com.if7100.entity.Modalidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModalidadRepository extends JpaRepository<Modalidad, Integer> {

    Modalidad findByCVTitulo(String CVTitulo);

    Modalidad findByCVDescripcion(String CVDescripcion);

}
