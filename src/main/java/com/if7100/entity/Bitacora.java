/**
 *
 */
package com.if7100.entity;


import java.util.Date;

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
	public Bitacora(Integer CIId, String CV_DNI_Usuario, String CVRol, String CV_Descripcion, Date CTFecha) {
		super();
		this.CVUsuario = CV_DNI_Usuario;
		this.CVRol= CVRol;
		this.CVDescripcion = CV_Descripcion;
		this.CIId = CIId;
		this.CTFecha = new Date();
	}

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Integer CI_Id_Bitacora;

	@Column (name = "CI_Id", nullable = false)
	private Integer CIId;


	@Column (name = "CV_DNI_Usuario", nullable = false)
	private String CVUsuario;

	@Column (name = "CV_Rol", nullable = false)
	private String CVRol;
	
	@Column (name = "CV_Descripcion", nullable = false)
	private String CVDescripcion;


	@Column (name = "CT_Fecha", nullable = false)
	private Date CTFecha;


	public Integer getCI_Id_Bitacora() {
		return CI_Id_Bitacora;
	}

	public void setCI_Id_Bitacora(Integer cI_Id_Bitacora) {
		CI_Id_Bitacora = cI_Id_Bitacora;
	}


	public Integer getCIId() {
		return CIId;
	}


	public void setCIId(Integer cIId) {
		CIId = cIId;
	}


	public String getCVUsuario() {
		return CVUsuario;
	}


	public void setCVUsuario(String cVUsuario) {
		CVUsuario = cVUsuario;
	}


	public String getCVRol() {
		return CVRol;
	}

	public void setCVRol(String cVRol) {
		CVRol = cVRol;
	}
	public String getCVDescripcion() {
		return CVDescripcion;
	}


	public void setCVDescripcion(String cVDescripcion) {
		CVDescripcion = cVDescripcion;
	}


	public Date getCTFecha() {
		return CTFecha;
	}


	public void setCTFecha(Date cTFecha) {
		CTFecha = cTFecha;
	}


}