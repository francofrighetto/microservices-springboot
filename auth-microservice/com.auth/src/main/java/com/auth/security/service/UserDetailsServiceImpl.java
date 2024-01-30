package com.auth.security.service;

import com.auth.security.entity.UsuarioActual;
import com.auth.security.entity.Usuario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service

public class UserDetailsServiceImpl implements UserDetailsService {
    private final static Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Autowired
    UsuarioService usuarioService;

    //@Override
    @Transactional
    public UserDetails loadUserByUsername(String celular) throws UsernameNotFoundException {
        
        Usuario usuario = usuarioService.getByCelularOrEmail(celular).get();

        return UsuarioActual.build(usuario);
    }
    
}
