package com.if7100.entity.relacionesEntity;

import com.if7100.entity.NivelEducativo;
import com.if7100.entity.Paises;
import com.if7100.entity.SituacionJuridica;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "ti_situacionjuridicapais")
public class SituacionJuridicaPaises {
    
     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_situacionjuridicapais;

    @ManyToOne
    @JoinColumn(name = "id_codigopais")
    private Paises pais;

    @ManyToOne
    @JoinColumn(name = "id_situacionjuridica")
    private SituacionJuridica situacionJuridica;

    // Getters y Setters

    public Integer getId_situacionjuridicapais() {
        return id_situacionjuridicapais;
    }

    public void setId_situacionjuridicapais(Integer id_situacionjuridicapais) {
        this.id_situacionjuridicapais = id_situacionjuridicapais;
    }

    public Paises getPais() {
        return pais;
    }

    public void setPais(Paises pais) {
        this.pais = pais;
    }

    public SituacionJuridica getSituacionJuridica() {
        return situacionJuridica;
    }

    public void setSituacionJuridica(SituacionJuridica situacionJuridica) {
        this.situacionJuridica = situacionJuridica;
    }


}
