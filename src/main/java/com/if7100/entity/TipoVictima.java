package com.if7100.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name= "ta_tipovictima")


/**
 * @author Liss
 * Fecha: 20 de abril del 2023
 */
public class TipoVictima {

	/**
	 *  Esta es la clase para el JPA Entity de la tabla femicidios.TA_Usuarios
	 */
	@Id
	@GeneratedValue (strategy= GenerationType.IDENTITY)
	private int CI_Codigo;
	
	@Column (name="CV_Titulo", nullable=false)
	private String CVTitulo;
	
	@Column (name="CV_Descripcion", nullable=false)
	private String CVDescripcion;
	
	
	
	public TipoVictima() {
		
	}
	
	
	public TipoVictima(String CV_Titulo, String CV_Descripcion) {
		super();
		this.CVTitulo= CV_Titulo;
		this.CVDescripcion= CV_Descripcion;
	}
	
	

	public int getCI_Codigo() {
		return CI_Codigo;
	}

	public void setCI_Codigo(int cI_Codigo) {
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
