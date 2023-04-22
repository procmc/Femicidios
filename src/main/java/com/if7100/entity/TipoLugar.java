/**
 * 
 */
package com.if7100.entity;

/**
 * @author kendall B
 * fecha: 11 de abril del 2023
 */

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name= "ta_tipolugar")

public class TipoLugar {

	/**
	 * Esta es la clase para el JPA entity de la tabla femicidios.TA_Usuarios
	 */
	public TipoLugar() {
		// TODO Auto-generated constructor stub
	}
	
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer CI_Codigo;
	
	@Column (name = "CV_Titulo", nullable = false)
	private String CVTitulo;
	
	@Column (name = "CV_Descripcion", nullable = false)
	private String CVDescripcion;
	
	
	public TipoLugar( String CVTitulo, String CVDescripcion) {
		super();
		this.CVTitulo = CVTitulo;
		this.CVDescripcion = CVDescripcion;
		// TODO Auto-generated constructor stub
	}
	
	
	public Integer getCI_Codigo() {
		return CI_Codigo;
	}

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
