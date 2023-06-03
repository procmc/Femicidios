package com.if7100.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.if7100.entity.Generos;
import com.if7100.entity.IdentidadGenero;
import com.if7100.repository.GeneroRepository;
import com.if7100.service.GenerosService;

@Service
public class GeneroImplement implements GenerosService {

	private GeneroRepository generoRepository;

	public GeneroImplement(GeneroRepository generoRepository) {
		super();
		this.generoRepository = generoRepository;
	}

	@Override
	public List<Generos> getAllGeneros() {

		return generoRepository.findAll();
	}

	@Override
	public List<Generos> obtencionGeneros(List<IdentidadGenero> iG) {
		List<Generos> listaGeneros = new ArrayList<>();
		for (int i = 0; i < iG.size(); i++) {
			if (generoRepository.findById((int) iG.get(i).getGenero()).get() != null) {
				listaGeneros.add(generoRepository.findById((int) iG.get(i).getGenero()).get());
			}

		}
		return listaGeneros;
	}

}