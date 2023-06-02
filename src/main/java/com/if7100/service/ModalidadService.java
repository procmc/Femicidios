package com.if7100.service;

import com.if7100.entity.Modalidad;
import java.util.List;

public interface ModalidadService {

    List<Modalidad> getAllModalidades();

    Modalidad saveModalidad(Modalidad modalidad);

    Modalidad getModalidadById(Integer Id);

    Modalidad updateModalidad(Modalidad modalidad);

    void deleteModalidadById(Integer Id);

    Modalidad getModalidadByTitulo(String CVTitulo);

    Modalidad getModalidadByDescripcion(String CVDescripcion);

}
