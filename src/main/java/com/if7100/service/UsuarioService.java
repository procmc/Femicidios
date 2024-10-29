/**
 * 
 */
package com.if7100.service;

import java.util.List;

import com.if7100.entity.Imputado;
import com.if7100.entity.Organizacion;
import com.if7100.entity.Paises;
import com.if7100.entity.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author Liss
 *         Fecha: 11 de abril del 2023
 */

public interface UsuarioService {

    //List<Organizacion> getAllOrganizacionPage(Pageable pageable);

    List<Usuario> getAllUsuarios();

    Usuario saveUsuario(Usuario usuario);

    Usuario getUsuarioById(Integer Id);

    Usuario updateUsuario(Usuario usuario);

    void deleteUsuarioById(Integer Id);

    Page<Usuario> getAllUsuariosPage(Pageable pageable);
}