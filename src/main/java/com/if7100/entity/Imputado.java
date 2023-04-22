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
 * @author Kijan
 *
 */
@Entity
@Table(name="ta_imputados") 
public class Imputado {

	/**
	 * 
	 */
	public Imputado() {
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	
	public Imputado(String cV_DNI, String cVNombre, String cVGenero, String cVOrientacionSexual, String cIEdad,
			String cVLugarNacimiento) {
		super();
		this.CVDni = cV_DNI;
		this.CVNombre = cVNombre;
		this.CVGenero = cVGenero;
		this.CVOrientacionSexual = cVOrientacionSexual;
		this.CIEdad = cIEdad;
		this.CVLugarNacimiento = cVLugarNacimiento;
	}




	@Id
	@GeneratedValue (strategy=GenerationType.IDENTITY)
	private int CI_Id;
	
	public int getCI_Id() {
		return CI_Id;
	}

	public void setCI_Id(int cI_Id) {
		CI_Id = cI_Id;
	}
	
	@Column(name="cv_dni",nullable=false)
	private String CVDni;
	
	@Column(name="cv_nombre",nullable=false)
	private String CVNombre;
	
	@Column(name="cv_genero",nullable=false)
	private String CVGenero;
	
	@Column(name="cv_orientacionsexual",nullable=false)
	private String CVOrientacionSexual;
	
	@Column(name="ci_edad",nullable=false)
	private String CIEdad;
	
	@Column(name="cv_lugarnacimiento",nullable=false)
	private String CVLugarNacimiento;

	public String getCVDni() {
		return CVDni;
	}

	public void setCVDni(String cVDni) {
		CVDni = cVDni;
	}


	public String getCVNombre() {
		return CVNombre;
	}


	public void setCVNombre(String cVNombre) {
		CVNombre = cVNombre;
	}


	public String getCVGenero() {
		return CVGenero;
	}


	public void setCVGenero(String cVGenero) {
		CVGenero = cVGenero;
	}


	public String getCVOrientacionSexual() {
		return CVOrientacionSexual;
	}


	public void setCVOrientacionSexual(String cVOrientacionSexual) {
		CVOrientacionSexual = cVOrientacionSexual;
	}


	public String getCIEdad() {
		return CIEdad;
	}


	public void setCIEdad(String cIEdad) {
		CIEdad = cIEdad;
	}


	public String getCVLugarNacimiento() {
		return CVLugarNacimiento;
	}


	public void setCVLugarNacimiento(String cVLugarNacimiento) {
		CVLugarNacimiento = cVLugarNacimiento;
	}
	
	

	
	
	

}
