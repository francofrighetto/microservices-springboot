package com.auth.security.service;

import com.auth.security.repository.UsuarioRepository;
import java.util.Optional;
import com.auth.security.entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;

@Service
//@Transactional
public class UsuarioService {
    
    @Autowired
    UsuarioRepository usuarioRepository;
    
    public Optional<Usuario> getByCelular(String celular) {
        return usuarioRepository.findByCelular(celular);
    }
    
    public Optional<Usuario> getByCelularOrEmail(String celularOEmail) {
        return usuarioRepository.findByCelularOrEmail(celularOEmail, celularOEmail);
    }

    public Optional<Usuario> getByTokenPassword(String tokenPassword) {
        return usuarioRepository.findByTokenPassword(tokenPassword);
    }
    
    public boolean existsByCelular(String celular) {
        return usuarioRepository.existsByCelular(celular);
    }

    public boolean existsByEmail(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    public void save(Usuario usuario) {
        usuarioRepository.save(usuario);
    }
    public Page<Usuario> buscarTodos(String busqueda, Pageable pageable){


        return usuarioRepository.findAll(busqueda, pageable);
    }
}
