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
		this.CV_Titulo = CV_Titulo;
		this.CV_Descripcion = CV_Descripcion;
		this.CI_Pais = CI_Pais;
		
	}
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Integer CI_Id;
	
	@Column (name = "CV_Titulo", nullable = false)
	private  String CV_Titulo;
	
	@Column (name = "CV_Descripcion", nullable = false)
	private String CV_Descripcion ;	
	
	@Column (name = "CI_Pais", nullable = false)
	private Integer CI_Pais ;
	
	public Integer getCI_Id() {
		return CI_Id;
	}
	public void setCI_Id(Integer cI_Id) {
		CI_Id = cI_Id;
	}
	public String getCV_Titulo() {
		return CV_Titulo;
	}
	public void setCV_Titulo(String cV_Titulo) {
		CV_Titulo= cV_Titulo;
	}
	public String getCV_Descripcion() {
		return CV_Descripcion;
	}
	public void setCV_Descripcion(String cV_Descripcion) {
		CV_Descripcion = cV_Descripcion;
	}
	public Integer getCI_Pais() {
		return CI_Pais;
	}
	public void setCI_Pais(Integer cI_Pais) {
		CI_Pais = cI_Pais;
	}
		
		
	}

