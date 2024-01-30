package com.auth.security.controller;

import io.swagger.annotations.ApiOperation;
import com.auth.comun.dto.Message;
import com.auth.security.dto.JwtDto;
import com.auth.security.dto.LoginUsuario;
import com.auth.security.dto.NuevoUsuario;
import com.auth.security.entity.Rol;
import com.auth.security.enums.RolNombre;
import com.auth.security.jwt.JwtProvider;
import com.auth.security.service.RolService;
import com.auth.security.service.UsuarioService;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.validation.Valid;
import com.auth.security.entity.Usuario;
import java.text.ParseException;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin

public class AuthCotroller {
    private final static Logger logger = LoggerFactory.getLogger(AuthCotroller.class);
    
    @Autowired
    PasswordEncoder passwordEncoder;
    
    @Autowired
    AuthenticationManager authenticationManager;
    
    @Autowired
    UsuarioService usuarioService;
    
    @Autowired
    RolService rolService;
    
    @Autowired
    JwtProvider jwtProvider;
    
    @PostMapping("/nuevo")
    public ResponseEntity<?> nuevo(@Valid @RequestBody NuevoUsuario nuevoUsuario, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return new ResponseEntity(new Message(400,"campos mal puestos o email inválido"),HttpStatus.BAD_REQUEST);

        if (usuarioService.existsByCelular(nuevoUsuario.getCelular())) 
            return new ResponseEntity(new Message(400,"ese celular ya existe"),HttpStatus.OK);

        if (usuarioService.existsByEmail(nuevoUsuario.getEmail())) 
            return new ResponseEntity(new Message(400,"ese email ya existe"),HttpStatus.OK);

        Usuario usuario = new Usuario(
            nuevoUsuario.getNombre(),
            nuevoUsuario.getCelular(),
            nuevoUsuario.getEmail(),
            passwordEncoder.encode(nuevoUsuario.getPassword()),
            null
        );
        
        Set<Rol> roles = new HashSet<>();

        Optional<Rol> rolOptional = rolService.getByRolNombre(RolNombre.ROLE_USER);
               
        if (nuevoUsuario.getRoles().contains("admin")){
            roles.add(rolService.getByRolNombre(RolNombre.ROLE_ADMIN).get());
        }

        else if (nuevoUsuario.getRoles().contains("super")){
            roles.add(rolService.getByRolNombre(RolNombre.ROLE_SUPER).get());
        }
        else{ 
            roles.add(rolService.getByRolNombre(RolNombre.ROLE_USER).get());  
        }

        usuario.setRoles(roles);

        usuarioService.save(usuario);
        
        return new ResponseEntity(new Message(0,"usuario guardados"),HttpStatus.OK);       
    }

    @PostMapping("/login")
    public ResponseEntity<JwtDto> login(@Valid @RequestBody LoginUsuario loginUsuario, BindingResult bindingResult) {
        String celular = loginUsuario.getCelular();
        String password = loginUsuario.getPassword();
        
        if (bindingResult.hasErrors()){
            return new ResponseEntity(new Message(400,"campos mal puestos"),HttpStatus.BAD_REQUEST);
        }
        if ( ! (usuarioService.existsByCelular(celular) || usuarioService.existsByEmail(celular)) ){
         return new ResponseEntity(new Message(400,"El usuario "+celular+" no se encuentra registrado"),HttpStatus.OK);
        }


        Authentication authentication=
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(celular,password));

        SecurityContextHolder.getContext().setAuthentication(authentication);
                
        String jwt = jwtProvider.generateToken(authentication);
        
        JwtDto jwtDto = new JwtDto(jwt);
        
        return new ResponseEntity(jwtDto,HttpStatus.OK);       
    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<JwtDto> refreshToken(@RequestBody JwtDto jwtDto) throws ParseException {
        String token=jwtProvider.refreshToken(jwtDto);

        JwtDto jwt = new JwtDto(token);
        
        return new ResponseEntity(jwt,HttpStatus.OK);       
    }

    //@ApiIgnore
    @ApiOperation("Obtiene un Usuario por Celular")
    @GetMapping("/buscaPorCelular/{celular}")
    public ResponseEntity<Usuario> buscaPoId(@PathVariable("celular") String celular) {
        if (!usuarioService.existsByCelular(celular)) {
            return new ResponseEntity(new Message(404,"El Usuario no existe"), HttpStatus.BAD_REQUEST);
        }
        Usuario usuario = usuarioService.getByCelularOrEmail(celular).get();
        return new ResponseEntity(usuario, HttpStatus.OK);
    }

    @ApiOperation("Edita un usuario")
    @PostMapping("/guardar")
    public ResponseEntity<Object> saveUser(@Valid @RequestBody Usuario user) {
        Map<String, Object> response = new HashMap<>();
        try {
            this.usuarioService.save(user);
            return new ResponseEntity(new Message(0, "Se editó con éxito el usuario."),
                    HttpStatus.OK);
        } catch (Exception e) {
                    System.out.println(e);
            return new ResponseEntity(new Message(500, "Error al editar usuario."),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
