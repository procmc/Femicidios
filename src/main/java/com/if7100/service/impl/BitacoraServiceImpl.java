/**
 * 
 */
package com.if7100.service.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.if7100.entity.Bitacora;
import com.if7100.repository.BitacoraRepository;
import com.if7100.service.BitacoraService;

/**
 * @author tisha
 *
 */

@Service
public class BitacoraServiceImpl implements BitacoraService{

	
	private BitacoraRepository bitacoraRepository;
	

	public BitacoraServiceImpl (BitacoraRepository bitacoraRepository) {
		super();
		this.bitacoraRepository= bitacoraRepository;
	}

	@Override
	public List<Bitacora> getAllBitacoras() {
	   return bitacoraRepository.findAll();
	}

	@Override
	public Page<Bitacora> getAllBitacorasPage(Pageable pageable){
		return bitacoraRepository.findAll(pageable);
	}

	@Override
	public Bitacora saveBitacora (Bitacora bitacora) {
		return bitacoraRepository.save(bitacora);
	}
	
	@Override
	public Bitacora getBitacoraById(Integer id) {
		return bitacoraRepository.findById(id).get();
	}
	@Override
	public Bitacora updateBitacora(Bitacora bitacora) {
		return bitacoraRepository.save(bitacora);
	}
	
	/*@Override
	public void crearNuevaBitacora(Bitacora bitacora) {
		bitacoraRepository.save(bitacora);
	}*/
	
	
	@Override
	public void deleteBitacoraById(Integer id) {
		bitacoraRepository.deleteById(id);
	}
	
	@Override
	public Bitacora getBitacoraByCVUsuario(String CVUsuario) {
		return bitacoraRepository.findByCVUsuario(CVUsuario);
	}
	
	
}
