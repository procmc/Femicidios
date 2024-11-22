package com.if7100.service;

import java.util.List;

import com.if7100.entity.Perfil;
import com.if7100.entity.Usuario;
import com.if7100.entity.UsuarioPerfil;

public interface UsuarioPerfilService {
    
    boolean usuarioTieneRol(String cvcedulausuario, Integer ciidperfil);

    List<UsuarioPerfil> getAllUsuarioPerfil();

    void deleteUsuarioPerfilById( Integer id);

    UsuarioPerfil saveUsuarioPerfil(UsuarioPerfil usuarioPerfil);

    List<UsuarioPerfil> getUsuarioPerfilByUsuario(Usuario usuario);
}
