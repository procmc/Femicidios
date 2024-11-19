package com.if7100.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.if7100.entity.TipoRelacionFamiliar;

public interface TipoRelacionFamiliarService {
	List<TipoRelacionFamiliar> getAllTipoRelacionFamiliar();

	Page<TipoRelacionFamiliar> getAllTipoRelacionFamiliar(Pageable pageable);

	TipoRelacionFamiliar saveTipoRelacionFamiliar(TipoRelacionFamiliar identidadGenero);

	TipoRelacionFamiliar getTipoRelacionFamiliarById(Integer Id);

	TipoRelacionFamiliar updateTipoRelacionFamiliar(TipoRelacionFamiliar tiporelacion);

	void deleteTipoRelacionFamiliarById(Integer Id);

	List<TipoRelacionFamiliar> getTipoRelacionFamiliarByCodigoPais(Integer codigoPais);

}
