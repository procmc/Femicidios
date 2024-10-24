package com.if7100.service;

import com.if7100.entity.TipoRelacion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TipoRelacionService {

    List<TipoRelacion> getAllTipoRelaciones();

    TipoRelacion saveTipoRelacion(TipoRelacion tipoRelacion);

    TipoRelacion getTipoRelacionById(Integer Id);

    TipoRelacion updateTipoRelacion(TipoRelacion tipoRelacion);

    void deleteTipoRelacionById(Integer Id);

    TipoRelacion getTipoRelacionByTitulo(String CVTitulo);

    TipoRelacion getTipoRelacionaByDescripcion(String CVDescripcion);

    Page<TipoRelacion> getAllTipoRelacionesPage(Pageable pageable);
}
