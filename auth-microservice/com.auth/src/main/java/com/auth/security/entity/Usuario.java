package com.auth.security.entity;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity

@Getter
@Setter

public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    private String nombre;

    @NotNull
    @Column(unique = true)
    private String celular;

    @NotNull
    private String email;

    @NotNull
    private String password;

    private String tokenPassword;

    @NotNull
    @ManyToMany
    @JoinTable(name="usuario_rol",joinColumns=@JoinColumn(name="usuario_id"),
    inverseJoinColumns=@JoinColumn(name="rol_id"))
    private Set<Rol> roles = new HashSet<>();

    public Usuario(String nombre, String celular, String email, String password, String tokenPassword) {
        this.nombre = nombre;
        this.celular = celular;
        this.email = email;
        this.password = password;
        this.tokenPassword=tokenPassword;
    }

    public Usuario() {
    }

    @Override
    public String toString() {
        return "Usuario{" + "id=" + id + ", nombre=" + nombre + ", celular=" + celular + ", email=" + email + ", password=" + password + ", tokenPassword=" + tokenPassword + ", roles=" + roles + '}';
    }
}
