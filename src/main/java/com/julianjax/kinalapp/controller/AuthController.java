package com.julianjax.kinalapp.controller;

import com.julianjax.kinalapp.entity.Usuario;
import com.julianjax.kinalapp.service.IUsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final IUsuarioService usuarioService;

    public AuthController(IUsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // Registro de nuevo usuario — cualquiera puede acceder sin estar logueado
    @PostMapping("/registro")
    public ResponseEntity<?> registrar(@Valid @RequestBody Usuario usuario) {
        try {
            // Si no mandan rol, por defecto es USER
            if (usuario.getRol() == null || usuario.getRol().isBlank()) {
                usuario.setRol("USER");
            }
            // Validar que el rol sea válido
            String rol = usuario.getRol().toUpperCase();
            if (!rol.equals("ADMIN") && !rol.equals("USER")) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "El rol solo puede ser ADMIN o USER"));
            }
            usuario.setRol(rol);
            Usuario nuevo = usuarioService.registrar(usuario);
            // No devolver la contraseña en la respuesta
            nuevo.setPassword(null);
            return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // Devuelve info del usuario que está logueado actualmente
    @GetMapping("/me")
    public ResponseEntity<?> usuarioActual() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || auth.getPrincipal().equals("anonymousUser")) {
            return ResponseEntity.status(401).body(Map.of("error", "No autenticado"));
        }
        // Devolvemos el nombre y el rol para que el frontend sepa qué mostrar
        String rol = auth.getAuthorities().stream()
                .findFirst()
                .map(a -> a.getAuthority().replace("ROLE_", ""))
                .orElse("USER");
        return ResponseEntity.ok(Map.of(
                "username", auth.getName(),
                "rol", rol
        ));
    }
}
