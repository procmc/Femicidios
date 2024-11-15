package com.if7100.service;

import java.util.List;

import com.if7100.entity.Modalidad;
import com.if7100.entity.NivelEducativo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NivelEducativoService {

	List<NivelEducativo> getAllNivelEducativo();

	NivelEducativo saveNivelEducativo(NivelEducativo nivelEducativo);

	NivelEducativo getNivelEducativoById(Integer Id);

	NivelEducativo updateNivelEducativo(NivelEducativo nivelEducativo);

	void deleteNivelEducativoById(Integer Id);

	NivelEducativo getNivelEducativoByTitulo(String CV_Titulo);

	NivelEducativo getNivelEducativoByDescripcion(String CV_Descripcion);

	Page<NivelEducativo> getAllNivelEducativoPage(Pageable pageable);

	List<NivelEducativo> getNivelEducativoByCodigoPais(Integer codigoPais);

}
