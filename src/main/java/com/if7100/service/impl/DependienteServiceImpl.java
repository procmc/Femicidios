/**
 * 
 */
package com.if7100.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.if7100.entity.Dependiente;
import com.if7100.entity.Hecho;
import com.if7100.entity.TipoRelacion;
import com.if7100.entity.TipoRelacionFamiliar;
import com.if7100.entity.Victima;
import com.if7100.repository.DependienteRepository;
import com.if7100.repository.TipoRelacionFamiliarRepository;
import com.if7100.repository.TipoRelacionRepository;
import com.if7100.repository.VictimaRepository;
import com.if7100.service.DependienteService;

import jakarta.transaction.Transactional;

/**
 * @author Hadji
 *
 */

@Service
public class DependienteServiceImpl implements DependienteService {

	private DependienteRepository dependienteRepository;

	private TipoRelacionFamiliarRepository tipoRelacionFamiliarRepository;
	private VictimaRepository victimaRepository;

	public DependienteServiceImpl(DependienteRepository dependienteRepository,
			TipoRelacionFamiliarRepository tipoRelacionFamiliarRepository,
			VictimaRepository victimaRepository) {
		super();
		this.dependienteRepository = dependienteRepository;
		this.tipoRelacionFamiliarRepository = tipoRelacionFamiliarRepository;
		this.victimaRepository = victimaRepository;
	}

	/*
	 * @Override
	 * public List<Dependiente> findAllDependientesConRelacionesYVictimas() {
	 * return dependienteRepository.findAllDependientesConRelacionesYVictimas();
	 * }
	 */

	@Override
	public List<Dependiente> getAllDependiente() {
		return dependienteRepository.findAll();
	}

	@Override
	public Page<Dependiente> getAllDependientePage(Pageable pageable) {
		return dependienteRepository.findAll(pageable);
	}

	//

	@Override
	public List<TipoRelacionFamiliar> getAllTipoRelacionesFamilires() {
		List<Dependiente> dependientes = dependienteRepository.findAll();
		List<TipoRelacionFamiliar> tipoRelacionFamiliares = new ArrayList<>();

		for (Dependiente dependiente : dependientes) {
			if (dependiente.getTipoRelacionFamiliar() != null) {
				tipoRelacionFamiliares.add(dependiente.getTipoRelacionFamiliar());
			}
		}
		return tipoRelacionFamiliares;
	}

	@Override
	public List<TipoRelacionFamiliar> getAllTipoRelacionesFamiliaresPage(Pageable pageable) {
		Page<Dependiente> dependientes = dependienteRepository.findAll(pageable);
		List<TipoRelacionFamiliar> tipoRelacionFamiliares = new ArrayList<>();

		for (Dependiente dependiente : dependientes) {
			// Añadir directamente la víctima asociada al hecho
			if (dependiente.getTipoRelacionFamiliar() != null) {
				tipoRelacionFamiliares.add(dependiente.getTipoRelacionFamiliar());
			}
		}

		return tipoRelacionFamiliares;
	}

	@Override
	public List<Victima> getAllVictimas() {
		List<Dependiente> dependientes = dependienteRepository.findAll();
		List<Victima> victimas = new ArrayList<>();

		for (Dependiente dependiente : dependientes) {
			// Añadir directamente la víctima asociada al hecho
			if (dependiente.getVictima() != null) {
				victimas.add(dependiente.getVictima());
			}
		}

		return victimas;
	}

	@Override
	public List<Victima> getAllVictimasPage(Pageable pageable) {
		Page<Dependiente> dependientes = dependienteRepository.findAll(pageable);
		List<Victima> victimas = new ArrayList<>();

		for (Dependiente dependiente : dependientes) {
			// Añadir directamente la víctima asociada al hecho
			if (dependiente.getVictima() != null) {
				victimas.add(dependiente.getVictima());
			}
		}

		return victimas;
	}

	//

	@Override
	public Dependiente saveDependiente(Dependiente dependiente) {
		return dependienteRepository.save(dependiente);
	}

	@Override
	public Dependiente getDependienteById(Integer Id) {
		return dependienteRepository.findById(Id).get();
	}

	/*
	 * @Override
	 * public Dependiente updateDependiente (Dependiente dependiente){
	 * return dependienteRepository.save(dependiente);
	 * }
	 */

	@Override
	public void deleteDependienteById(Integer Id) {
		dependienteRepository.deleteById(Id);
	}

	@Override
	public Dependiente getDependienteByCVDNI(String CVNombre) {
		return dependienteRepository.findByCVDNI(CVNombre);
	}

	public List<Dependiente> getDependientesByCodigoPais(Integer codigoPais) {
		return dependienteRepository.findByCodigoPaisVictima(codigoPais);
	}

}
