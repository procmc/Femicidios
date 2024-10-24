/**
 * 
 */
package com.if7100.entity;


import org.hibernate.annotations.ManyToAny;

/**
 * @author Liss
 * Fecha: 11 de abril del 2023
 */
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "TA_Usuarios", uniqueConstraints = @UniqueConstraint(columnNames="CV_Cedula"))

public class Usuario{

	/**
	 * Esta es la clase para el JPA Entity de la tabla femicidios.TA_Usuarios
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int CI_Id;

	@Column(name = "CV_Cedula", nullable = false)
	private String CVCedula;

	@Column(name = "CV_Nombre", nullable = false)
	private String CVNombre;

	@Column(name = "CV_Apellidos", nullable = false)
	private String CVApellidos;
	
	@Column(name = "CI_Perfil", nullable = false)
	private int CIPerfil;
	
	@Column(name = "TC_Clave", nullable = false)
	private String TCClave;
	
	@Column(name = "codigo_pais", nullable = false)
	private Integer codigoPais;
	
	@ManyToOne
	@JoinColumn(name = "CI_Codigo_Organizacion", referencedColumnName = "CI_Codigo_Organizacion", nullable = false)
	private Organizacion organizacion;
	
	
	/*@ManyToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinTable(
			name="TA_Usuarios_Perfiles",
			joinColumns= @JoinColumn(name="CI_Usuario_Id", referencedColumnName= "CI_Id"),
			inverseJoinColumns=@JoinColumn(name="CI_Perfil_Id", referencedColumnName= "CI_Id")
			)
	
	private Collection<Perfil> perfil;*/

	public Usuario() {

	}
	
	public Usuario(Usuario usuario) {
		this.CI_Id = usuario.CI_Id;
		this.CVCedula = usuario.CVCedula;
		this.CVNombre = usuario.CVNombre;
		this.CVApellidos = usuario.CVApellidos;
		this.CIPerfil = usuario.CIPerfil;
		this.codigoPais = usuario.codigoPais;
		this.TCClave = usuario.TCClave;
		this.organizacion = usuario.organizacion;
	}
	

	public Usuario(String cVCedula, String cVNombre, String cVApellidos, int cIPerfil, int codigoPais, String tCClave, Organizacion organizacion) {
		super();
		CVCedula = cVCedula;
		CVNombre = cVNombre;
		CVApellidos = cVApellidos;
		CIPerfil = cIPerfil;
		this.codigoPais = codigoPais;
		TCClave = tCClave;
		this.organizacion = organizacion;
	}


	public int getCI_Id() {
		return CI_Id;
	}

	public void setCI_Id(int cI_Id) {
		CI_Id = cI_Id;
	}

	public String getCVCedula() {
		return CVCedula;
	}

	public void setCVCedula(String cICedula) {
		CVCedula = cICedula;
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

	public String getTCClave() {
		return TCClave;
	}

	public void setTCClave(String tCClave) {
		TCClave = tCClave;
	}

	public int getCIPerfil() {
		return CIPerfil;
	}

	public void setCIPerfil(int cIPerfil) {
		CIPerfil = cIPerfil;
	}
	
	//pais
	public Integer getCodigoPais() {
		return this.codigoPais;
	}

	public void setCodigoPais(int codigoPais) {
		this.codigoPais = codigoPais;
	}

	public Organizacion getOrganizacion() {
		return this.organizacion;
	}

	public void setOrganizacion(Organizacion organizacion) {
		this.organizacion = organizacion;
	}
}

