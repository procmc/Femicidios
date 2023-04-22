/*
 * 
 */
package com.if7100.entity;

/**
 * @author Tishary Foster
 * Fecha: 1 de abril del 2023
 */

import jakarta.persistence.Column;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table (name = "ta_modalidades")

/**
 * Clase para el JPA Entity de la tabla femicidio.ta_modalidad*/
public class Modalidad {
    
	
	public Modalidad() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 
	 */
	public Modalidad(String CV_Titulo, String CV_Descripcion) {
		super();
		this.CVTitulo = CV_Titulo;
		this.CVDescripcion = CV_Descripcion;
	}
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Integer CI_Codigo;
	
	@Column (name = "CV_Titulo", nullable = false)
	private String CVTitulo;
	
	@Column (name = "CV_Descripcion", nullable = false)
	private String CVDescripcion;

	public Integer getCI_Codigo() {
		return CI_Codigo;
	}

	//Encapsulamiento de las variables
	
	public void setCI_Codigo(Integer cI_Codigo) {
		CI_Codigo = cI_Codigo;
	}

	public String getCVTitulo() {
		return CVTitulo;
	}

	public void setCVTitulo(String cVTitulo) {
		CVTitulo = cVTitulo;
	}

	public String getCVDescripcion() {
		return CVDescripcion;
	}

	public void setCVDescripcion(String cVDescripcion) {
		CVDescripcion = cVDescripcion;
	}

	


	

	
	
}

