/**
 * 
 */
package com.if7100.service;

import java.util.List;


import com.if7100.entity.Bitacora;
/**
 * @author tishary foster
 * 
 */
public interface BitacoraService {
	
	List <Bitacora> getAllBitacoras();
	Bitacora saveBitacora (Bitacora bitacora);
	Bitacora getBitacoraById(Integer id);
	Bitacora updateBitacora(Bitacora bitacora);
    //	void crearNuevaBitacora (Bitacora bitacora);
	void deleteBitacoraById(Integer id);
	Bitacora getBitacoraByCVUsuario(String CVUsuario);
	

}
