/**
 * 
 */
package com.if7100.service;

import java.util.List;

import com.if7100.entity.Perfil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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


    Page<Perfil> getAllPerfilesPage(Pageable pageable);
}
