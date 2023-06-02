/**
 *
 */
package com.if7100.service;

import java.util.List;

import com.if7100.entity.TipoOrganismo;

/**
 * @author Adam Smasher
 */
public interface TipoOrganismoService {

	List<TipoOrganismo> getAllTipoOrganismos();

	TipoOrganismo saveTipoOrganismo(TipoOrganismo tipoOrganismo);

	TipoOrganismo getTipoOrganismoByCodigo(Integer Codigo);

	TipoOrganismo updateTipoOrganismo(TipoOrganismo tipoOrganismo);

	void deleteTipoOrganismoByCodigo(Integer Codigo);

	TipoOrganismo getTipoOrganismoByCVTitulo(String CVTitulo);


}