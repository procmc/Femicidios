/**
 * 
 */
package com.if7100.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import com.if7100.entity.Modalidad;
import com.if7100.repository.ModalidadRepository;
import com.if7100.service.ModalidadService;

/**
 * @author tisha
 *Fecha 19 de abril 2023
 */

@Service
public class ModalidadServiceImpl implements ModalidadService{

	
	private ModalidadRepository modalidadRepository;
	
	public ModalidadServiceImpl (ModalidadRepository modalidadRepository) {
		super();
		this.modalidadRepository= modalidadRepository;
	}

	@Override
	public List<Modalidad> getAllModalidad() {
	   return modalidadRepository.findAll();
	}
	
	@Override
	public Modalidad saveModalidad (Modalidad modalidad) {
		return modalidadRepository.save(modalidad);
	}
	
	@Override
	public Modalidad getModalidadById(Integer codigo) {
		return modalidadRepository.findById(codigo).get();
	}
	@Override
	public Modalidad updateModalidad(Modalidad modalidad) {
		return modalidadRepository.save(modalidad);
	}
	
	@Override
	public void deleteModalidadById(Integer codigo) {
		modalidadRepository.deleteById(codigo);
	}
	
	@Override
	public Modalidad getModalidadByCVTitulo(String CVTitulo) {
		return modalidadRepository.findByCVTitulo(CVTitulo);
	}
	
	
}
