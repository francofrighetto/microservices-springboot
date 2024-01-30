package com.auth.security.entity;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UsuarioActual implements UserDetails {
    private final static Logger logger = LoggerFactory.getLogger(UsuarioActual.class);

    private String nombre;
    private String celular;
    private String email;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    public UsuarioActual() {
    }

    public UsuarioActual(String nombre, String celular, String email, String password, Collection<? extends GrantedAuthority> authorities) {
        this.nombre = nombre;
        this.celular = celular;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    public static UsuarioActual build(Usuario usuario) {
        //logger.error("UsuarioActual build "+usuario.getNombre()+"<<<");

        List<GrantedAuthority> authorities=
                usuario.getRoles().stream().map(rol -> new SimpleGrantedAuthority(
                        rol.getRolNombre().name())).collect(Collectors.toList());
        return new UsuarioActual(usuario.getNombre(),usuario.getCelular(),usuario.getEmail(),usuario.getPassword(), authorities);                
    }
 
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return celular;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }
}
