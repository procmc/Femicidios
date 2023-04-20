/**
 * 
 */
package com.if7100.service;

import java.util.List;

import com.if7100.entity.Usuario;

/**
 * @author Carlos Morales Castro
 * Fecha: 1 abril 2023 
 */

public interface UsuarioService {
	
	List<Usuario> getAllUsuarios();
	
	Usuario saveUsuario (Usuario usuario);
	
	Usuario getUsuarioById(Integer Id);
	
	Usuario updateUsuario (Usuario usuario);
	
	void deleteUsuarioById(Integer Id);
	
	Usuario getUsuarioByCVNombre(String CVNombre);
	
}
