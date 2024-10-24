package com.if7100.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "ta_situacion_juridica")
public class SituacionJuridica {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer CI_Codigo;

    @Column(name = "CV_Titulo", nullable = false)
    private String CVTitulo;

    @Column(name = "CV_Descripcion", nullable = false)
    private String CVDescripcion;

    public SituacionJuridica() {
    }

//    public SituacionJuridica(Integer CI_Codigo, String CVTitulo, String CVDescripcion) {
//        this.CI_Codigo = CI_Codigo;
//        this.CVTitulo = CVTitulo;
//        this.CVDescripcion = CVDescripcion;
//    }

    public SituacionJuridica(String CVTitulo, String CVDescripcion) {
        this.CVTitulo = CVTitulo;
        this.CVDescripcion = CVDescripcion;
    }

    public Integer getCI_Codigo() {
        return CI_Codigo;
    }

    public void setCI_Codigo(Integer CI_Codigo) {
        this.CI_Codigo = CI_Codigo;
    }

    public String getCVTitulo() {
        return CVTitulo;
    }

    public void setCVTitulo(String CVTitulo) {
        this.CVTitulo = CVTitulo;
    }

    public String getCVDescripcion() {
        return CVDescripcion;
    }

    public void setCVDescripcion(String CVDescripcion) {
        this.CVDescripcion = CVDescripcion;
    }
}
