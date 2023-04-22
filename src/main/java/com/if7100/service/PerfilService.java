/**
 * 
 */
package com.if7100.service;

import java.util.List;

import com.if7100.entity.Perfil;

/**
 * @author David
 *
 */
public interface PerfilService {

	List<Perfil> getAllPerfiles();
	
	Perfil savePerfil (Perfil perfil);
	
	Perfil getPerfilById(Integer id);
	
	Perfil updatePerfil(Perfil perfil);
	
	void deletePerfilById(Integer id);

	
}
