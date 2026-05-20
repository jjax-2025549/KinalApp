package com.julianjax.kinalapp.repository;

import com.julianjax.kinalapp.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    List<Usuario> findByEstado(int estado);

    // Spring Security lo necesita para buscar por username al hacer login
    Optional<Usuario> findByUsername(String username);

    // Para validar en el registro que no se repita el username o email
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
