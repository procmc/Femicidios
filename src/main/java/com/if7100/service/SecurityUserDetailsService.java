package com.if7100.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.stereotype.Service;

import com.if7100.entity.Usuario;
import com.if7100.repository.UsuarioRepository;

@Service

public class SecurityUserDetailsService implements UserDetailsService  {

	@Autowired
	private UsuarioRepository usuarioRepository;

	public SecurityUserDetailsService(UsuarioRepository usuarioRepository) {
		super();
		this.usuarioRepository = usuarioRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario=usuarioRepository.findByCVCedula(username);
		UserBuilder builder=null;
		if(usuario!=null) {
		builder=User.withUsername(username);
		builder.disabled(false);
		builder.password(usuario.getTCClave());
		builder.authorities(new SimpleGrantedAuthority("ROLE_USER"));
		}else {
			throw new UsernameNotFoundException("Usuario o contraseña invalidos");
		}
		return builder.build();
	}
	
}