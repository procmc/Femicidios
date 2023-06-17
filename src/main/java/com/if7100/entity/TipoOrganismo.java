/**
 * 
 */
package com.if7100.entity;

/**
 * @author Adam Smasher
 */

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name= "ta_tipoorganismo")
public class TipoOrganismo {


	public TipoOrganismo() {
		// TODO Auto-generated constructor stub
	}
	
	
	public TipoOrganismo( String CVTitulo, String CVDescripcion, String CVPaises) {
		super();
		this.CVTitulo = CVTitulo;
		this.CVDescripcion = CVDescripcion;
		this.CVPaises = CVPaises;
	}
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer CI_Codigo;
	
	@Column (name = "CV_Titulo", nullable = false)
	private String CVTitulo;
	
	@Column (name = "CV_Descripcion", nullable = false)
	private String CVDescripcion;
	
	@Column (name = "CV_Paises", nullable = false)
	private String CVPaises;
	
	
	
	
	
	public String getCVPaises() {
		return CVPaises;
	}


	public void setCVPaises(String cVPaises) {
		CVPaises = cVPaises;
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
