package com.julianjax.kinalapp.security;

import com.julianjax.kinalapp.entity.Usuario;
import com.julianjax.kinalapp.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder; // <--- INYECTAMOS EL ENCRIPTADOR OFICIAL

    // Constructor para que Spring inyecte las dependencias correctas
    public DataInitializer(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        // Si el usuario admin no existe, lo creamos de forma correcta
        if (usuarioRepository.findByUsername("admin").isEmpty()) {
            Usuario admin = new Usuario();
            admin.setUsername("admin");

            // CRÍTICO: Encriptamos la contraseña usando el bean oficial del sistema
            admin.setPassword(passwordEncoder.encode("kinal2026"));

            admin.setEmail("admin@kinal.edu.gt");
            admin.setRol("ADMIN");
            admin.setEstado(1);

            usuarioRepository.save(admin);
            System.out.println("✅ [DataInitializer] Usuario admin creado exitosamente con BCrypt oficial.");
        }
    }
}