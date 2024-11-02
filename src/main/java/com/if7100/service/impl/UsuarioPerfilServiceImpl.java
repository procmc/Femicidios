package com.if7100.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.if7100.repository.PerfilRepository;
import com.if7100.repository.UsuarioPerfilRepository;
import com.if7100.service.UsuarioPerfilService;

@Service
public class UsuarioPerfilServiceImpl implements UsuarioPerfilService{
    
    @Autowired
    private UsuarioPerfilRepository usuarioPerfilRepository;

    @Autowired
    private PerfilRepository perfilRepository; // Asegúrate de tener un repositorio para la entidad Perfil

    @Override
    public boolean usuarioTieneRol(String cvcedulausuario, Integer ciidperfil) {
        // Obtiene los IDs de roles asociados al usuario desde ti_usuario_perfil
        List<Integer> rolesUsuario = usuarioPerfilRepository.findRolesByUsuarioId(cvcedulausuario);

        // Verifica si el ID de rol que necesitas está en la lista de roles del usuario
        return rolesUsuario.contains(ciidperfil);
    }
}
