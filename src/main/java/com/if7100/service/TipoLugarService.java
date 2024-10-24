package com.if7100.service;

import java.util.List;
import com.if7100.entity.TipoLugar; //se debe importar para trabajar el usuario
import com.if7100.entity.TipoRelacion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author kendall B
 *Fecha: 11 de abril del 2023
 */
public interface TipoLugarService {// se crean primero los servicios

    List<TipoLugar> getAllTipoLugares();

    TipoLugar saveTipoLugar(TipoLugar tipoLugar);

    TipoLugar getTipoLugarByCodigo(Integer Codigo);

    TipoLugar updateTipoLugar(TipoLugar tipoLugar);

    void deleteTipoLugarByCodigo(Integer Codigo);

    TipoLugar getTipoLugarByTitulo(String CVTitulo);

    TipoLugar getTipoLugarByDescripcion(String CVDescripcion);

    Page<TipoLugar> getAllTipoLugaresPage(Pageable pageable);
}