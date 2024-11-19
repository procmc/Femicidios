package com.if7100.entity.relacionesEntity;

import com.if7100.entity.Paises;
import com.if7100.entity.TipoRelacion;
import com.if7100.entity.TipoRelacionFamiliar;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "ti_tiporelacionfamiliarpais")
public class TipoRelacionFamiliarPaises {
    
      @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_tiporelacionfamiliarpais;

    @ManyToOne
    @JoinColumn(name = "id_codigopais")
    private Paises pais;

    @ManyToOne
    @JoinColumn(name = "id_tiporelacionfamiliar")
    private TipoRelacionFamiliar tipoRelacionFamiliar;

    // Getters y Setters

    public Integer getId_tipoRelacionFamiliarpais() {
        return id_tiporelacionfamiliarpais;
    }

    public void setId_tipoRelacionFamiliarpais(Integer id_tipoRelacionFamiliarpais) {
        this.id_tiporelacionfamiliarpais = id_tipoRelacionFamiliarpais;
    }

    public Paises getPais() {
        return pais;
    }

    public void setPais(Paises pais) {
        this.pais = pais;
    }

    public TipoRelacionFamiliar getTipoRelacionFamiliar() {
        return tipoRelacionFamiliar;
    }

    public void setTipoRelacionFamiliar(TipoRelacionFamiliar tipoRelacionFamiliar) {
        this.tipoRelacionFamiliar = tipoRelacionFamiliar;
    }

}
