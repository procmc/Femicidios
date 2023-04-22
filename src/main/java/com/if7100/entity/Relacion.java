package com.if7100.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "ta_tiporelacion")
public class Relacion {

	public Relacion() {

	}
	
	public Relacion(String cVtitulo, String cVdescripcion) {
		super();
	
		this.CVtitulo = cVtitulo;
		this.CVdescripcion = cVdescripcion;
	}

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Integer CI_codigo;
	
	@Column (name = "CV_titulo", nullable = false)
	private String CVtitulo;

	@Column (name = "CV_descripcion", nullable = false)
	private String CVdescripcion;

	public Integer getCI_codigo() {
		return CI_codigo;
	}

	public void setCI_codigo(Integer cI_codigo) {
		CI_codigo = cI_codigo;
	}

	public String getCVtitulo() {
		return CVtitulo;
	}

	public void setCVtitulo(String cVtitulo) {
		CVtitulo = cVtitulo;
	}

	public String getCVdescripcion() {
		return CVdescripcion;
	}

	public void setCVdescripcion(String cVdescripcion) {
		CVdescripcion = cVdescripcion;
	}
	
	
}
