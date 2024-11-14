package com.if7100.repository;

import com.if7100.entity.Modalidad;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ModalidadRepository extends JpaRepository<Modalidad, Integer> {

    Modalidad findByCVTitulo(String CVTitulo);

    Modalidad findByCVDescripcion(String CVDescripcion);

    @Query("SELECT m FROM Modalidad m JOIN ModalidadesPaises mp ON m.CI_Codigo = mp.modalidad.CI_Codigo WHERE mp.pais.Id = :codigoPais")
    List<Modalidad> findAllByCodigoPais(@Param("codigoPais") Integer codigoPais);

}
