package com.if7100.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "hechos_imputados")
public class HechoImputado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer CI_Id;

    @Column(name = "CI_Hecho", nullable = false)
    private Integer CIHecho;

    @Column(name = "CI_Imputado", nullable = false)
    private Integer CIImputado;

    public HechoImputado(){

    }

    public HechoImputado(Integer CIHecho, Integer CIImputado){
        super();
        this.CIHecho = CIHecho;
        this.CIImputado = CIImputado;
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

    public Integer getCIImputado() {
        return CIImputado;
    }

    public void setCIImputado(Integer CIImputado) {
        this.CIImputado = CIImputado;
    }
}
