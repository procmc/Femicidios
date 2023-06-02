/**

 * 
 */
package com.if7100.entity;
import jakarta.persistence.Column;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * @author Gènesis
 *
 */

@Entity
@Table(name =  "ta_niveleducativo" )
public class NivelEducativo {

	/**
	 * esta es la clase para el JPA entity de la table femicidios.ta_niveleducativo
	 */
	public NivelEducativo() {
		// TODO Auto-generated constructor stub
	}
	public NivelEducativo(String CV_Titulo, String CV_Descripcion, Integer CI_Pais) {
		
		super();
		this.CVTitulo = CV_Titulo;
		this.CVDescripcion = CV_Descripcion;
		this.CI_Pais = CI_Pais;
		
	}
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Integer CI_Id;
	
	@Column (name = "CV_Titulo", nullable = false)
	private  String CVTitulo;
	
	@Column (name = "CV_Descripcion", nullable = false)
	private String CVDescripcion;
	
	@Column (name = "CI_Pais", nullable = false)
	private Integer CI_Pais ;
	
	public Integer getCI_Id() {
		return CI_Id;
	}
	public void setCI_Id(Integer cI_Id) {
		CI_Id = cI_Id;
	}
	public String getCVTitulo() {
		return CVTitulo;
	}
	public void setCVTitulo(String cV_Titulo) {
		CVTitulo = cV_Titulo;
	}
	public String getCVDescripcion() {
		return CVDescripcion;
	}
	public void setCVDescripcion(String cV_Descripcion) {
		CVDescripcion = cV_Descripcion;
	}
	public Integer getCI_Pais() {
		return CI_Pais;
	}
	public void setCI_Pais(Integer cI_Pais) {
		CI_Pais = cI_Pais;
	}
		
		
	}

