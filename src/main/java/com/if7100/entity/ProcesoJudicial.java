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
@Table(name="TA_ProcesoJudicial")
public class ProcesoJudicial {

	/**
	 * 
	 */
	public ProcesoJudicial() {
		// TODO Auto-generated constructor stub
	}
	
	public ProcesoJudicial(String CVEstado, int CIDenunciante, int CIPersonasImputadas, String CVPartes) {
		super();
		this.CVEstado=CVEstado;
		this.CIDenunciante=CIDenunciante;
		this.CIPersonasImputadas=CIPersonasImputadas;
		this.CVPartes=CVPartes;
	}
	
	@Id
	@GeneratedValue (strategy=GenerationType.IDENTITY)
	private int CI_IdProceso;
	
	@Column(name="CV_Estado",nullable=false)
	private String CVEstado;
	
	@Column(name="CI_Denunciante",nullable=false)
	private int CIDenunciante;
	
    @Temporal(TemporalType.TIMESTAMP)
	@Column(name="CD_FechaApertura")
	private Date CDFechaApertura;
	
	@Column(name="CI_PersonasImputadas",nullable=false)
	private int CIPersonasImputadas;
	
	@Column(name="CV_Partes",nullable=false)
	private String CVPartes;

	

	/**
	 * @return the cI_IdProceso
	 */
	public int getCI_IdProceso() {
		return CI_IdProceso;
	}

	/**
	 * @param cI_IdProceso the cI_IdProceso to set
	 */
	public void setCI_IdProceso(int cI_IdProceso) {
		CI_IdProceso = cI_IdProceso;
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
	 * @return the cIDenunciante
	 */
	public int getCIDenunciante() {
		return CIDenunciante;
	}

	/**
	 * @param cIDenunciante the cIDenunciante to set
	 */
	public void setCIDenunciante(int cIDenunciante) {
		CIDenunciante = cIDenunciante;
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
	public String getCVPartes() {
		return CVPartes;
	}

	/**
	 * @param cVPartes the cVPartes to set
	 */
	public void setCVPartes(String cVPartes) {
		CVPartes = cVPartes;
	}
	
	@PrePersist
    protected void onCreate() {
		CDFechaApertura = new Date();
    }
	
	
}