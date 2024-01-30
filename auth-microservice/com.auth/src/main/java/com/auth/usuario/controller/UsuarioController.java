/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.auth.usuario.controller;

import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.auth.comun.dto.Message;
import com.auth.security.entity.Usuario;
import com.auth.security.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/usuario")
@CrossOrigin
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @ApiOperation("Obtiene una lista de usuarios paginada")
    @GetMapping("/lista")
    public ResponseEntity<Object> listaUsuarios(
            @RequestParam(name = "sortHeader", defaultValue = "celular") String sortHeader,
            @RequestParam(name = "sortOrder", defaultValue = "desc") String sortOrder,
            @RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(name = "busqueda", defaultValue = "") String busqueda) {
        Map<String, Object> response = new HashMap<>();

        try {

            List<Usuario> usuarios = new ArrayList<Usuario>();
            Pageable paging = PageRequest.of(pageNumber, pageSize);

            Page<Usuario> pageUsuarios = usuarioService.buscarTodos(busqueda, paging);

            usuarios = pageUsuarios.getContent();

            response.put("comprobantes", usuarios);
            response.put("paginaActual", pageUsuarios.getNumber());
            response.put("totalRegistros", pageUsuarios.getTotalElements());
            response.put("totalPaginas", pageUsuarios.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(new Message(500, "No se pueden obtener los datos."),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
