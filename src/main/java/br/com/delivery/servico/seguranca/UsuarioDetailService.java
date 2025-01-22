package br.com.delivery.servico.seguranca;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import br.com.delivery.entidade.Usuario;
import br.com.delivery.repositorio.UsuarioRepositorio;

@Component
public class UsuarioDetailService implements UserDetailsService {
	
	@Autowired
	private UsuarioRepositorio usuarioRepositorio;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = this.usuarioRepositorio.findByEmail(username).
				orElseThrow(()-> new UsernameNotFoundException("Usuário não encontrado"));
		return new org.springframework.security.core.userdetails.User(usuario.getEmail(), usuario.getSenha(), new ArrayList<>());
	}

}
