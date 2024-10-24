package com.if7100.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "ti_dependiente_victima")
public class DependienteVictima {
    
      @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer CI_Id_Dependientevictima;

    @ManyToOne
    @JoinColumn(name = "CI_ID_Victima", referencedColumnName = "CI_Id")
    private Victima victima;

    @ManyToOne
    @JoinColumn(name = "CI_Codigo_Dependiente", referencedColumnName = "CI_Codigo")
    private Dependiente dependiente;

    public DependienteVictima(){}

    public DependienteVictima(int CI_Id_Dependientevictima, Dependiente dependiente, Victima victima){
        super();
        this.CI_Id_Dependientevictima = CI_Id_Dependientevictima;
        this.dependiente = dependiente;
        this.victima = victima;

    }

     // Getters y Setters
     public Integer getCI_Id_DependienteVictima() {
        return CI_Id_Dependientevictima;
    }

    public void setCI_Id_DependienteVictima(Integer CI_Id_Dependientevictima) {
        this.CI_Id_Dependientevictima = CI_Id_Dependientevictima;
    }

    public Dependiente getDependiente() {
        return dependiente;
    }

    public void setDependiente(Dependiente dependiente) {
        this.dependiente = dependiente;
    }

    public Victima getVictima() {
        return victima;
    }

    public void setVictima(Victima victima) {
        this.victima = victima;
    }
}
