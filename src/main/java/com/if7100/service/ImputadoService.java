/**
 * 
 */
package com.if7100.service;

import java.util.List;

import com.if7100.entity.Imputado;
import com.if7100.entity.Usuario;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author Kijan
 *
 */
public interface ImputadoService {
 List<Imputado> getAllImputados();

 Imputado saveImputado(Imputado imputado);
 
 Imputado getImputadoById(int id);

 Imputado updateImputado(Imputado imputado);
 
 void deleteImputadoById(int id);
 
 Imputado getImputadoByCVNombre(String CV_Nombre);

    Page<Imputado> getAllImputadosPage(Pageable pageable);


public List<Imputado> findByCodigoPais(Integer codigoPais);//obtiene los usuarios por codigo país


}
