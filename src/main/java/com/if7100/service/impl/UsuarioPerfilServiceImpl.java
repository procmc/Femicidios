package com.if7100.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.if7100.entity.Perfil;
import com.if7100.entity.TipoRelacion;
import com.if7100.entity.Usuario;
import com.if7100.entity.UsuarioPerfil;
import com.if7100.entity.relacionesEntity.TipoRelacionPaises;
import com.if7100.repository.PerfilRepository;
import com.if7100.repository.UsuarioPerfilRepository;
import com.if7100.repository.relacionesRepository.TipoRelacionPaisesRepository;
import com.if7100.service.UsuarioPerfilService;

@Service
public class UsuarioPerfilServiceImpl implements UsuarioPerfilService{
    
    @Autowired
    private UsuarioPerfilRepository usuarioPerfilRepository;

    @Override
    public boolean usuarioTieneRol(String cvcedulausuario, Integer ciidperfil) {
        // Obtiene los IDs de roles asociados al usuario desde ti_usuario_perfil
        List<Integer> rolesUsuario = usuarioPerfilRepository.findRolesByUsuarioId(cvcedulausuario);

        // Verifica si el ID de rol que necesitas está en la lista de roles del usuario
        return rolesUsuario.contains(ciidperfil);
    }


    @Override
    public List<UsuarioPerfil> getAllUsuarioPerfil() {
        return usuarioPerfilRepository.findAll();
    }

    @Override
    public void deleteUsuarioPerfilById(Integer id) {
        usuarioPerfilRepository.deleteById(id);

    }

    @Override
    public UsuarioPerfil saveUsuarioPerfil(UsuarioPerfil usuarioPerfil) {
        return usuarioPerfilRepository.save(usuarioPerfil);
    }

    @Override
    public List<UsuarioPerfil> getUsuarioPerfilByUsuario(Usuario usuario) {
        return usuarioPerfilRepository.findByUsuario(usuario);

    }

}
