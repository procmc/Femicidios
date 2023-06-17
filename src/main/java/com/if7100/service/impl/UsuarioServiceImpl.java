/**
 * 
 */
package com.if7100.service.impl;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.if7100.entity.Usuario;
import com.if7100.repository.UsuarioRepository;
import com.if7100.service.UsuarioService;

/**
 * @author Liss
 * Fecha: 11 de abril del 2023
 */
@Service
public class UsuarioServiceImpl implements UsuarioService{

	

	private UsuarioRepository usuarioRepository;
	
	
    public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
		super();
		this.usuarioRepository=usuarioRepository;
	}
    
    @Override
    public List<Usuario> getAllUsuarios(){
    	return usuarioRepository.findAll();
    }

    @Override
    public Page<Usuario> getAllUsuariosPage(Pageable pageable){
        return usuarioRepository.findAll(pageable);
    }

    @Override
    public Usuario saveUsuario(Usuario usuario){
    	usuario.setTCClave(new BCryptPasswordEncoder().encode(usuario.getTCClave()));
    	return usuarioRepository.save(usuario);
    }
    
    @Override
    public Usuario getUsuarioById(Integer Id){
    	return usuarioRepository.findById(Id).get();
    }
    
    @Override
    public Usuario updateUsuario(Usuario usuario){
    	usuario.setTCClave(new BCryptPasswordEncoder().encode(usuario.getTCClave()));
    	return usuarioRepository.save(usuario);
    }
    
    @Override
    public void deleteUsuarioById(Integer Id){
    	 usuarioRepository.deleteById(Id);
    }
}
