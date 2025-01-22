package br.com.delivery.repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.delivery.entidade.Usuario;

public interface UsuarioRepositorio extends JpaRepository<Usuario, String> {

	Optional<Usuario> findByEmail(String email);

}
