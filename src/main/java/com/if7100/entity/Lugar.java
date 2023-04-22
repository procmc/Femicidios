/**
 * 
 */
package com.if7100.entity;

/**
 * @author Julio Jarquin
 * Fecha: 20 de abril del 2023
 */
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name= "ta_lugar")


public class Lugar {

	/**
	 *  Esta es la clase para el JPA Entity de la tabla femicidios.TA_Usuarios
	 */
	@Id
	@GeneratedValue (strategy= GenerationType.IDENTITY)
	private int CI_Codigo;
	
	@Column (name="CIF_DNI_Victima", nullable=false)
	private int CIF_DNI_Victima;
	
	@Column (name="CV_Descripcion", nullable=false)
	private String CV_Descripcion;
	
	@Column (name="CIF_Tipo_Lugar", nullable=false)
	private int CIF_Tipo_Lugar;
	
	@Column (name="CV_Direccion", nullable=false)
	private String CV_Direccion;
	
	@Column (name="CV_Ciudad", nullable=false)
	private String CV_Ciudad;
	
	@Column (name="CV_Pais", nullable=false)
	private int CV_Pais;
	
	@Transient
	private String Titulo;
	
	public Lugar() {
	}
	
	public Lugar(int CIF_DNI_Victima, String CV_Descripcion, int CIF_Tipo_Lugar, String CV_Direccion, String CV_Ciudad, int CV_Pais, String Titulo) {
		super();
		this.CIF_DNI_Victima= CIF_DNI_Victima;
		this.CV_Descripcion= CV_Descripcion;
		this.CIF_Tipo_Lugar= CIF_Tipo_Lugar;
		this.CV_Direccion= CV_Direccion;
		this.CV_Ciudad= CV_Ciudad;
		this.CV_Pais= CV_Pais;
		this.Titulo=Titulo;
	}

	public int getCI_Codigo() {
		return CI_Codigo;
	}

	public void setCI_Codigo(int cI_Codigo) {
		CI_Codigo = cI_Codigo;
	}

	public int getCIF_DNI_Victima() {
		return CIF_DNI_Victima;
	}

	public void setCIF_DNI_Victima(int cIF_DNI_Victima) {
		CIF_DNI_Victima = cIF_DNI_Victima;
	}

	public String getCV_Descripcion() {
		return CV_Descripcion;
	}

	public void setCV_Descripcion(String cV_Descripcion) {
		CV_Descripcion = cV_Descripcion;
	}

	public int getCIF_Tipo_Lugar() {
		return CIF_Tipo_Lugar;
	}

	public void setCIF_Tipo_Lugar(int cIF_Tipo_Lugar) {
		CIF_Tipo_Lugar = cIF_Tipo_Lugar;
	}

	public String getCV_Direccion() {
		return CV_Direccion;
	}

	public void setCV_Direccion(String cV_Direccion) {
		CV_Direccion = cV_Direccion;
	}

	public String getCV_Ciudad() {
		return CV_Ciudad;
	}

	public void setCV_Ciudad(String cV_Ciudad) {
		CV_Ciudad = cV_Ciudad;
	}

	public int getCV_Pais() {
		return CV_Pais;
	}

	public void setCV_Pais(int cV_Pais) {
		CV_Pais = cV_Pais;
	}

	public String getTitulo() {
		return Titulo;
	}

	public void setTitulo(String titulo) {
		Titulo = titulo;
	}
	
	

	
}
