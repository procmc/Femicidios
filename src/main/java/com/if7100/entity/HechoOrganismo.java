package com.if7100.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "ta_hechos_organismos")
public class HechoOrganismo {





    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer CI_Id;

    @Column(name = "CI_Hecho", nullable = false)
    private Integer CIHecho;

    @Column(name = "CI_Organismo", nullable = false)
    private Integer CIOrganismo;

    public HechoOrganismo(){

    }

    public HechoOrganismo(Integer CIHecho, Integer CIOrganismo){
        super();
        this.CIHecho = CIHecho;
        this.CIOrganismo = CIOrganismo;
    }

    public HechoOrganismo(Integer CI_Id,Integer CIHecho, Integer CIOrganismo){
        super();
        this.CI_Id = CI_Id;
        this.CIHecho = CIHecho;
        this.CIOrganismo = CIOrganismo;
    }

    public Integer getCI_Id() {
        return CI_Id;
    }

    public void setCI_Id(Integer CI_Id) {
        this.CI_Id = CI_Id;
    }

    public Integer getCIHecho() {
        return CIHecho;
    }

    public void setCIHecho(Integer CIHecho) {
        this.CIHecho = CIHecho;
    }

    public Integer getCIOrganismo() {
        return CIOrganismo;
    }

    public void setCIOrganismo(Integer CIOrganismo) {
        this.CIOrganismo = CIOrganismo;
    }
}
