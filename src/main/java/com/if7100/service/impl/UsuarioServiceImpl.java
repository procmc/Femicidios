/**
 * 
 */
package com.if7100.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.if7100.entity.Usuario;
import com.if7100.repository.UsuarioRepository;
import com.if7100.service.UsuarioService;

/**
 * @author Carlos Morales Castro
 * Fecha: 1 abril 2023 
 */

@Service
public class UsuarioServiceImpl implements UsuarioService{
	
	private UsuarioRepository usuarioRepository;
	
	public UsuarioServiceImpl ( UsuarioRepository usuarioRepository)
	{
		super();
		this.usuarioRepository =usuarioRepository;
	}
	
	@Override
	public List<Usuario> getAllUsuarios(){
		return usuarioRepository.findAll(); 
	}
	
	@Override
	public Usuario saveUsuario(Usuario usuario){
		return usuarioRepository.save(usuario); 
	}
	
		@Override
	public Usuario getUsuarioById(Integer Id){
		return usuarioRepository.findById(Id).get(); 
	}
	
	@Override
	public Usuario updateUsuario (Usuario usuario){
		return usuarioRepository.save(usuario); 
	}
	
	@Override
	public void deleteUsuarioById(Integer Id){
		usuarioRepository.deleteById(Id); 
	}
	
	@Override
	public Usuario getUsuarioByCVNombre(String CVNombre){
		return usuarioRepository.findByCVNombre(CVNombre); 
	}
	
	

}
