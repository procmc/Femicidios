/**
 * 
 */
package com.if7100.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * @author David
 *
 */
@Entity
@Table(name = "TA_Perfil")
public class Perfil {
	
	public Perfil() {
		// TODO Auto-generated constructor stub
	}

    public Perfil(String CV_Descripcion, String CV_rol) {
        this.CVDescripcion = CV_Descripcion;
        this.CVRol = CV_rol;
    }
    
    public Perfil(Perfil perfil) {
    	this.CVDescripcion = perfil.CVDescripcion;
        this.CVRol = perfil.CVRol;
	}
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer CI_Id;

    @Column(name = "CV_Descripcion", nullable = false)
    private String CVDescripcion;

    @Column(name = "CV_rol", nullable = false)
    private String CVRol;

	public Integer getCI_Id() {
		return CI_Id;
	}

	public void setCI_Id(Integer cI_Id) {
		CI_Id = cI_Id;
	}

	public String getCVDescripcion() {
		return CVDescripcion;
	}

	public void setCVDescripcion(String cVDescripcion) {
		CVDescripcion = cVDescripcion;
	}

	public String getCVRol() {
		return CVRol;
	}

	public void setCVRol(String cVRol) {
		CVRol = cVRol;
	}


}