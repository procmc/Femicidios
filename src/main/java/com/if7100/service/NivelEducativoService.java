package com.if7100.service;

import java.util.List;

import com.if7100.entity.NivelEducativo;

public interface NivelEducativoService {
	
List<NivelEducativo>getAllNivelEducativo();
	
	NivelEducativo saveNivelEducativo(NivelEducativo nivelEducativo);
	NivelEducativo getNivelEducativoById(Integer Id);
	NivelEducativo updateNivelEducativo(NivelEducativo nivelEducativo);
	void deleteNivelEducativoById(Integer Id);

	NivelEducativo getNivelEducativoByTitulo(String CV_Titulo);

	NivelEducativo getNivelEducativoByDescripcion(String CV_Descripcion);

}
