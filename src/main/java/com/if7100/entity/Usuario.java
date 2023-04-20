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
 * @author Carlos Morales Castro
 * Fecha: 1 abril 2023
 */

@Entity
@Table(name = "TA_Usuarios")

public class Usuario {

	/**
	 * 
	 */
	public Usuario() {
		// TODO Auto-generated constructor stub
	}
	
	public Usuario(String CVNombre, String CVApellidos) {
		super();
		this.CVNombre = CVNombre;
		this.CVApellidos = CVApellidos;
		// TODO Auto-generated constructor stub
	}
	
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Integer CI_Id;
	
	@Column (name ="CV_Nombre", nullable = false)
	private String CVNombre;
	
	@Column (name ="CV_Apellidos", nullable = false)
	private String CVApellidos;

	public Integer getCI_Id() {
		return CI_Id;
	}

	public void setCI_Id(Integer cI_Id) {
		CI_Id = cI_Id;
	}

	public String getCVNombre() {
		return CVNombre;
	}

	public void setCVNombre(String cVNombre) {
		CVNombre = cVNombre;
	}

	public String getCVApellidos() {
		return CVApellidos;
	}

	public void setCVApellidos(String cVApellidos) {
		CVApellidos = cVApellidos;
	}
	
	
	

}
