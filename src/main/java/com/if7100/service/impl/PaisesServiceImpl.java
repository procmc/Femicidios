package com.if7100.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.if7100.entity.Paises;
import com.if7100.repository.PaisesRepository;
import com.if7100.service.PaisesService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class PaisesServiceImpl implements PaisesService {

    private final PaisesRepository paisesRepository;

    @Autowired
    public PaisesServiceImpl(PaisesRepository paisesRepository) {
        this.paisesRepository = paisesRepository;
    }


	@Override
	public Paises getPaisByISO2(String iso2) {
		return paisesRepository.findByISO2(iso2);
	}
	
    @Override
    public List<Paises> getAllPaises() {
        try {
            return paisesRepository.findAll(); 
        } catch (Exception e) {
            
            e.printStackTrace();
            return null; 
        }
    }


    @Override
    public Paises getPaisByID(int id) {
    Optional<Paises> paisOptional = paisesRepository.findById(id);
    
    // Verifica si el país existe y devuelve el objeto Paises o lanza una excepción
    if (paisOptional.isPresent()) {
        return paisOptional.get();
    } else {
        throw new EntityNotFoundException("País con ID " + id + " no encontrado");
    }
    }
}
