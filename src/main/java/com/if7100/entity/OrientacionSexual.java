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
 * @author Ronny Salgado
 *
 */
@Entity
@Table(name="ta_orientacionessexuales")
public class OrientacionSexual {

	/**
	 * 
	 */
	public OrientacionSexual() {
		// TODO Auto-generated constructor stub
	}
	public OrientacionSexual(String CVTitulo, String CVDescripcion) {
		super();
		this.CVTitulo = CVTitulo;
		this.CVDescripcion = CVDescripcion;
		
	}
	
	@Id
	@GeneratedValue (strategy=GenerationType.IDENTITY)
	private int CI_Codigo;
	
	@Column(name="CV_Titulo",nullable=false)
	private String CVTitulo;
	
	@Column(name="CV_Descripcion",nullable=false)
	private String CVDescripcion;

	public int getCI_Codigo() {
		return CI_Codigo;
	}
	public void setCI_Codigo(int cI_Codigo) {
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
