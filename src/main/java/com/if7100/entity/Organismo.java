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
 * @author Adam Smasher
 *
 */
@Entity
@Table(name="ta_organismos")
public class Organismo {


	public Organismo() {
		// TODO Auto-generated constructor stub
	}
	
	public Organismo(String CVNombre, String CVRol, String CVTipo_Organismo, String CVContacto, int codigoPais) {
		super();
		this.CVNombre=CVNombre;
		this.CVRol=CVRol;
		this.CVTipo_Organismo=CVTipo_Organismo;
		this.CVContacto=CVContacto;
		this.codigoPais = codigoPais;
	}
	
	@Id
	@GeneratedValue (strategy=GenerationType.IDENTITY)
	private int CI_Id;
	
	@Column(name="CV_Nombre",nullable=false)
	private String CVNombre;
	
	@Column(name="CV_Rol",nullable=false)
	private String CVRol;
	
	@Column(name="CV_Tipo_Organismo",nullable=false)
	private String CVTipo_Organismo;
	
	@Column(name="CV_Contacto",nullable=false)
	private String CVContacto;
	
	@Column(name = "codigo_pais", nullable = false)
	private Integer codigoPais;

	public int getCI_Id() {
		return CI_Id;
	}
	public void setCI_Id(int cI_Id) {
		CI_Id = cI_Id;
	}
	
	public String getCVNombre() {
		return CVNombre;
	}
	public void setCVNombre(String cVNombre) {
		CVNombre = cVNombre;
	}

	public String getCVRol() {
		return CVRol;
	}

	public void setCVRol(String cVRol) {
		CVRol = cVRol;
	}
	
	public String getCVTipo_Organismo() {
		return CVTipo_Organismo;
	}

	public void setCVTipo_Organismo(String cVTipo_Organismo) {
		CVTipo_Organismo = cVTipo_Organismo;
	}

	public String getCVContacto() {
		return CVContacto;
	}

	public void setCVContacto(String cVContacto) {
		CVContacto = cVContacto;
	}
	
	// pais
	public Integer getCodigoPais() {
		return this.codigoPais;
	}

	public void setCodigoPais(int codigoPais) {
		this.codigoPais = codigoPais;
	}

}
	
	