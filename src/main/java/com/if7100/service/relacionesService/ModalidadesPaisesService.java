package com.if7100.service.relacionesService;

import java.util.List;

import com.if7100.entity.Modalidad;
import com.if7100.entity.relacionesEntity.ModalidadesPaises;

public interface ModalidadesPaisesService {
    
    List<ModalidadesPaises> getAllRelaciones();

    void deleteRelacionById(Integer id);

    ModalidadesPaises saveModalidadesPaises(ModalidadesPaises modalidadesPaises);

    List<ModalidadesPaises> getRelacionesByModalidad(Modalidad modalidad);

}
