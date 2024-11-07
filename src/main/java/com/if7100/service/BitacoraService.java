/**
 * 
 */
package com.if7100.service;

import java.util.List;

import com.if7100.entity.Bitacora;
import com.if7100.entity.Hecho;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author tishary foster
 * 
 */
public interface BitacoraService {

	List<Bitacora> getAllBitacoras();

	Bitacora saveBitacora(Bitacora bitacora);

	Bitacora getBitacoraById(Integer id);

	Bitacora updateBitacora(Bitacora bitacora);

	// void crearNuevaBitacora (Bitacora bitacora);
	void deleteBitacoraById(Integer id);

	Bitacora getBitacoraByCVUsuario(String CVUsuario);

	Page<Bitacora> getAllBitacorasPage(Pageable pageable);

	List<Bitacora> findByCodigoPais(Integer codigoPais);

}
