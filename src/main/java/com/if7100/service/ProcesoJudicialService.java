/**
 * 
 */
package com.if7100.service;

import java.util.List;

import com.if7100.entity.ProcesoJudicial;

/**
 * @author Dillan Bermúdez González
 *
 */
public interface ProcesoJudicialService {
 List<ProcesoJudicial> getAllProcesosJudiciales();

 ProcesoJudicial saveProcesoJudicial(ProcesoJudicial procesoJudicial);
 
 ProcesoJudicial getProcesoJudicialById(int CI_IdProceso);

 ProcesoJudicial updateProcesoJudicial(ProcesoJudicial procesoJudicial);
 
 void deleteProcesoJudicialById(int CI_IdProceso);
 
 ProcesoJudicial getProcesoJudicialByCIDenunciante(int CI_Denunciante);
 
}
