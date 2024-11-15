package com.if7100.entity.relacionesEntity;

import com.if7100.entity.NivelEducativo;
import com.if7100.entity.Paises;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "ti_niveleducativopais")
public class NivelEducativoPaises {
    
     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_niveleducativopais;

    @ManyToOne
    @JoinColumn(name = "id_codigopais")
    private Paises pais;

    @ManyToOne
    @JoinColumn(name = "id_niveleducativo")
    private NivelEducativo nivelEducativo;

    // Getters y Setters

    public Integer getId_niveleducativopais() {
        return id_niveleducativopais;
    }

    public void setId_niveleducativopais(Integer id_niveleducativopais) {
        this.id_niveleducativopais = id_niveleducativopais;
    }

    public Paises getPais() {
        return pais;
    }

    public void setPais(Paises pais) {
        this.pais = pais;
    }

    public NivelEducativo getNivelEducativo() {
        return nivelEducativo;
    }

    public void setNivelEducativo(NivelEducativo nivelEducativo) {
        this.nivelEducativo = nivelEducativo;
    }


}
