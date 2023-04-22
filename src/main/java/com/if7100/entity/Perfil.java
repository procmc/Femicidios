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

    public Perfil(String CV_username, String CV_password, String CV_role) {
        this.CVUsername = CV_username;
        this.CVPassword = CV_password;
        this.CVRole = CV_role;
    }
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer CI_Id;

    @Column(name = "CV_username", nullable = false, unique = true)
    private String CVUsername;

    @Column(name = "CV_password", nullable = false)
    private String CVPassword;

    @Column(name = "CV_role", nullable = false)
    private String CVRole;

	public Integer getCI_Id() {
		return CI_Id;
	}

	public void setCI_Id(Integer cI_Id) {
		CI_Id = cI_Id;
	}

	public String getCVUsername() {
		return CVUsername;
	}

	public void setCVUsername(String cVUsername) {
		CVUsername = cVUsername;
	}

	public String getCVPassword() {
		return CVPassword;
	}

	public void setCVPassword(String cVPassword) {
		CVPassword = cVPassword;
	}

	public String getCVRole() {
		return CVRole;
	}

	public void setCVRole(String cVRole) {
		CVRole = cVRole;
	}


}
