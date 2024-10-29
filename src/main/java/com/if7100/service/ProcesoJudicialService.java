/**
 * 
 */
package com.if7100.service;

import java.util.List;

import com.if7100.entity.Hecho;
import com.if7100.entity.ProcesoJudicial;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author Dillan Bermúdez González
 *
 */
public interface ProcesoJudicialService {

ProcesoJudicial findByCVEstado(String CVEstado);

 List<ProcesoJudicial> getAllProcesosJudiciales();

 ProcesoJudicial saveProcesoJudicial(ProcesoJudicial procesoJudicial);
 
 ProcesoJudicial getProcesoJudicialById(int CI_Id);

 ProcesoJudicial updateProcesoJudicial(ProcesoJudicial procesoJudicial);
 
 void deleteProcesoJudicialById(int CI_Id);
 

 Page<ProcesoJudicial> getAllProcesosJudicialesPage(Pageable pageable);

 //List<ProcesoJudicial> getProcesosJudicialesByCodigoPaisUsuario(Integer codigoPaisUsuario);

 List<ProcesoJudicial> findProcesoJudicialByCICodigoPais(Integer codigoPaisUsuario);

}
