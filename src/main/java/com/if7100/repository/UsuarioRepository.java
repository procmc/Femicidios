package com.if7100.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.if7100.entity.Usuario;
import java.util.List;

@Repository

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
	 Usuario findByCVCedula(String CV_Cedula);

}
