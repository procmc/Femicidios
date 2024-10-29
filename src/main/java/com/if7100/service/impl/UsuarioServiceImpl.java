/**
 * 
 */
package com.if7100.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.if7100.entity.Organizacion;
import com.if7100.entity.Usuario;
import com.if7100.repository.OrganizacionRepository;
import com.if7100.repository.UsuarioRepository;
import com.if7100.service.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private UsuarioRepository usuarioRepository;
    private OrganizacionRepository organizacionRepository;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, OrganizacionRepository organizacionRepository) {
        super();
        this.usuarioRepository = usuarioRepository;
        this.organizacionRepository = organizacionRepository;
    }

    /*@Override
    public List<Organizacion> getAllOrganizacionPage(Pageable pageable) {
        Page<Usuario> usuarios = usuarioRepository.findAll(pageable);
        List<Organizacion> organizacion = new ArrayList<>();

        for (Usuario usuario : usuarios) {
            organizacion.add(organizacionRepository.findById(usuario.getCICodigoOrganizacion()).orElse(new Organizacion()));
        }
        return organizacion;
    }*/

    @Override
    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    @Override
    public Page<Usuario> getAllUsuariosPage(Pageable pageable) {
        return usuarioRepository.findAll(pageable);
    }

    @Override
    public Usuario saveUsuario(Usuario usuario) {
        usuario.setTCClave(new BCryptPasswordEncoder().encode(usuario.getTCClave()));
        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario getUsuarioById(Integer Id) {
        return usuarioRepository.findById(Id).get();
    }

    @Override
    public Usuario updateUsuario(Usuario usuario) {
        usuario.setTCClave(new BCryptPasswordEncoder().encode(usuario.getTCClave()));
        return usuarioRepository.save(usuario);
    }

    @Override
    public void deleteUsuarioById(Integer Id) {
        usuarioRepository.deleteById(Id);
    }

}
