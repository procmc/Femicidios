/**
 * 
 */
package com.if7100.entity;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
/**
 * @author Dillan Bermúdez González
 *
 */
@Entity
@Table(name="ta_procesojudicial")
public class ProcesoJudicial {

	/**
	 * 
	 */
	public ProcesoJudicial() {
		// TODO Auto-generated constructor stub
	}
	
	public ProcesoJudicial(String CVEstado, Date CDFechaApertura, int CIPersonasImputadas, String CVAgravantes, String CVTipoDelito, Integer CICodigoPais) {
		super();
		this.CVEstado=CVEstado;
		this.CDFechaApertura= CDFechaApertura;
		this.CIPersonasImputadas=CIPersonasImputadas;
		this.CVAgravantes=CVAgravantes;
		this.CVTipoDelito=CVTipoDelito;
		this.CICodigoPais = CICodigoPais;
	}
	
	@Id
	@GeneratedValue (strategy=GenerationType.IDENTITY)
	private int CI_Id;
	
	@Column(name="CV_Estado",nullable=false)
	private String CVEstado;
	
    @Temporal(TemporalType.TIMESTAMP)
	@Column(name="CD_Fecha_Apertura")
	private Date CDFechaApertura;
	
	@Column(name="CI_Personas_Imputadas",nullable=false)
	private int CIPersonasImputadas;
	
	@Column(name="CV_Agravantes",nullable=false)
	private String CVAgravantes;

	@Column(name="CV_Tipo_Delito",nullable=false)
	private String CVTipoDelito;
	
	@Column(name = "CI_CodigoPais", nullable = false)
    private Integer CICodigoPais;

	/**
	 * @return the cI_IdProceso
	 */
	public int getCI_Id() {
		return CI_Id;
	}

	/**
	 * @param cI_IdProceso the cI_IdProceso to set
	 */
	public void setCI_Id(int cI_IdProceso) {
		CI_Id = cI_IdProceso;
	}

	/**
	 * @return the cVEstado
	 */
	public String getCVEstado() {
		return CVEstado;
	}

	/**
	 * @param cVEstado the cVEstado to set
	 */
	public void setCVEstado(String cVEstado) {
		CVEstado = cVEstado;
	}


	/**
	 * @return the cDFechaApertura
	 */
	public Date getCDFechaApertura() {
		return CDFechaApertura;
	}

	/**
	 * @param cDFechaApertura the cDFechaApertura to set
	 */
	public void setCDFechaApertura(Date cDFechaApertura) {
		CDFechaApertura = cDFechaApertura;
	}

	/**
	 * @return the cIPersonasImputadas
	 */
	public int getCIPersonasImputadas() {
		return CIPersonasImputadas;
	}

	/**
	 * @param cIPersonasImputadas the cIPersonasImputadas to set
	 */
	public void setCIPersonasImputadas(int cIPersonasImputadas) {
		CIPersonasImputadas = cIPersonasImputadas;
	}

	/**
	 * @return the cVPartes
	 */

	public String getCVAgravantes() {
		return CVAgravantes;
	}

	public void setCVAgravantes(String cVAgravantes) {
		CVAgravantes = cVAgravantes;
	}

	public String getCVTipoDelito() {
		return CVTipoDelito;
	}

	public void setCVTipoDelito(String cVTipoDelito) {
		CVTipoDelito = cVTipoDelito;
	}
	
	@PrePersist
    protected void onCreate() {
		CDFechaApertura = new Date();
    }

	public int getCICodigoPais() {
		return CICodigoPais;
	}

	public void setCICodigoPais(int CICodigoPais) {
		this.CICodigoPais = CICodigoPais;
	}
	
	
}