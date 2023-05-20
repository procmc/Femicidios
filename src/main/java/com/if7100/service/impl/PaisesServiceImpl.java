package com.if7100.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.if7100.entity.Generos;
import com.if7100.entity.IdentidadGenero;
import com.if7100.entity.Paises;
import com.if7100.repository.PaisesRepository;
import com.if7100.service.PaisesService;

@Service
public class PaisesServiceImpl implements PaisesService {
	private PaisesRepository paisesRepository;

	public PaisesServiceImpl(PaisesRepository paisesRepository) {
		super();
		this.paisesRepository = paisesRepository;
	}

	@Override
	public List<Paises> getAllPaises() {
		return paisesRepository.findAll();
	}

	@Override
	public List<Paises> obtencionPaisesRelacionados(List<IdentidadGenero> iG) {
		List<Paises> listaGeneros = new ArrayList<>();
		for (int i = 0; i < iG.size(); i++) {
			if (paisesRepository.findById((int) iG.get(i).getCodigoPais()).get() != null) {
				listaGeneros.add(paisesRepository.findById((int) iG.get(i).getCodigoPais()).get());
			}

		}
		return listaGeneros;
	}

}
