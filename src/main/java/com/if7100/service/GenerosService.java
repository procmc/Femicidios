package com.if7100.service;

import java.util.List;



import com.if7100.entity.Generos;
import com.if7100.entity.IdentidadGenero;

public interface GenerosService {
	List<Generos>getAllGeneros();
   List<Generos> obtencionGeneros(List<IdentidadGenero> iG);
}
