/**
 * 
 */
package com.if7100.service;

import java.util.List;

import com.if7100.entity.Imputado;

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
 
}
