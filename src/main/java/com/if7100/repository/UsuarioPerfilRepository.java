package com.if7100.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.if7100.entity.Usuario;
import com.if7100.entity.UsuarioPerfil;

@Repository
public interface UsuarioPerfilRepository extends JpaRepository<UsuarioPerfil, UsuarioPerfil.UsuarioPerfilId>{
    
    @Query("SELECT up.perfil.CI_Id FROM UsuarioPerfil up WHERE up.usuario.CVCedula = :cvcedulausuario")
    List<Integer> findRolesByUsuarioId(@Param("cvcedulausuario") String cvcedulausuario);

    List<UsuarioPerfil> findByUsuario(Usuario usuario);
}
