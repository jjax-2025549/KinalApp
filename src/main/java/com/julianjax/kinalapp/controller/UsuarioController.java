package com.julianjax.kinalapp.controller;

import com.julianjax.kinalapp.entity.Usuario;
import com.julianjax.kinalapp.service.IUsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final IUsuarioService usuarioService;

    public UsuarioController(IUsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<Usuario> registrar(@RequestBody Usuario usuario) {
        return new ResponseEntity<>(usuarioService.registrar(usuario), HttpStatus.CREATED);
    }
}