package com.if7100.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "ti_usuario_perfil")
public class UsuarioPerfil {

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer ci_id_usuarioperfil;


    @ManyToOne
    //@MapsId("cvcedulausurio") 
    @JoinColumn(name = "cv_cedula_usuario", referencedColumnName = "CV_Cedula")
    private Usuario usuario;
    
    @ManyToOne
    //@MapsId("ciidperfil") 
    @JoinColumn(name = "ci_id_perfil", referencedColumnName = "CI_Id")
    private Perfil perfil;

    // Constructor, getters y setters
    public UsuarioPerfil() {}

    public UsuarioPerfil(Integer ci_id_usuarioperfil, Usuario usuario, Perfil perfil) {
        this.ci_id_usuarioperfil = ci_id_usuarioperfil;
        this.usuario = usuario;
        this.perfil = perfil;
    }

    public Integer getCi_id_usuarioperfil() {
        return ci_id_usuarioperfil;
    }

    public void setCi_id_usuarioperfil(Integer ci_id_usuarioperfil) {
        this.ci_id_usuarioperfil = ci_id_usuarioperfil;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

}
