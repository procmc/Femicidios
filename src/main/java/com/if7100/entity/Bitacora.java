/**
 * 
 */
package com.if7100.entity;

import java.security.Timestamp;


/**
 * @author tisha
 *
 */

import jakarta.persistence.Column;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table (name = "th_bitacoras")

/**
 * Clase para el JPA Entity de la tabla femicidio.th_bitacora*/
public class Bitacora {
    
	public Bitacora() {
		
	}
	public Bitacora(String CV_DNI_Usuario, String CV_Descripcion) {
		super();
		this.CVUsuario = CV_DNI_Usuario;
		this.CVDescripcion = CV_Descripcion;
	}
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Integer CI_Id_Bitacora;
	
	@Column (name = "CV_Cedula", nullable = false)
	private String CVCedula;
	
	@Column (name = "CV_DNI_Usuario", nullable = false)
	private String CVUsuario;
	
	@Column (name = "CV_Descripcion", nullable = false)
	private String CVDescripcion;


	@Column (name = "CT_Fecha", nullable = false)
	private Timestamp CTFecha;


	public Integer getCI_Id_Bitacora() {
		return CI_Id_Bitacora;
	}


	public void setCI_Id_Bitacora(Integer cI_Id_Bitacora) {
		CI_Id_Bitacora = cI_Id_Bitacora;
	}


	public String getCVCedula() {
		return CVCedula;
	}


	public void setCVCedula(String cVCedula) {
		CVCedula = cVCedula;
	}


	public String getCVUsuario() {
		return CVUsuario;
	}


	public void setCVUsuario(String cVUsuario) {
		CVUsuario = cVUsuario;
	}


	public String getCVDescripcion() {
		return CVDescripcion;
	}


	public void setCVDescripcion(String cVDescripcion) {
		CVDescripcion = cVDescripcion;
	}


	public Timestamp getCTFecha() {
		return CTFecha;
	}


	public void setCTFecha(Timestamp cTFecha) {
		CTFecha = cTFecha;
	}
	

}