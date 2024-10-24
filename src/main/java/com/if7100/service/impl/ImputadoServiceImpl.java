package com.if7100.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.if7100.entity.Imputado;
import com.if7100.entity.Usuario;
import com.if7100.repository.ImputadoRepository;
import com.if7100.repository.UsuarioRepository;
import com.if7100.service.ImputadoService;

@Service
public class ImputadoServiceImpl implements ImputadoService {
	 
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	private ImputadoRepository imputadoRepository;

	public ImputadoServiceImpl(ImputadoRepository imputadoRepository) {
		super();
		this.imputadoRepository=imputadoRepository;
	}
	
	
	@Override
	public List<Imputado> getAllImputados(){
		return imputadoRepository.findAll();
	}

	@Override
	public Page<Imputado> getAllImputadosPage(Pageable pageable){
		return imputadoRepository.findAll(pageable);
	}

	@Override
	public Imputado saveImputado(Imputado imputado) {
		// TODO Auto-generated method stub
		return imputadoRepository.save(imputado);
	}


	@Override
	public Imputado getImputadoById(int id) {
		// TODO Auto-generated method stub
		return imputadoRepository.findById(id).get();
	}


	@Override
	public Imputado updateImputado(Imputado imputado) {
		// TODO Auto-generated method stub
		return imputadoRepository.save(imputado);
	}


	@Override
	public void deleteImputadoById(int id) {
		// TODO Auto-generated method stub
		imputadoRepository.deleteById(id);
	}
	
	public Imputado getImputadoByCV_Nombre(String CV_Nombre) {
		// TODO Auto-generated method stub
		return imputadoRepository.findByCVNombre(CV_Nombre);
	}


	@Override
	public Imputado getImputadoByCVNombre(String CV_Nombre) {
		// TODO Auto-generated method stub
		return null;
	}

	    //obtiene los usuarios por codigo país
    @Override
    public List<Imputado> findByCodigoPais(Integer codigoPais) {
        return imputadoRepository.findByCodigoPais(codigoPais);
    }
}
