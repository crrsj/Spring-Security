package br.com.delivery.servico.seguranca;

import java.io.IOException;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.delivery.entidade.Usuario;
import br.com.delivery.repositorio.UsuarioRepositorio;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FiltroDeSeguranca extends OncePerRequestFilter {

	@Autowired
	TokenServico tokenServico;
	@Autowired
	UsuarioRepositorio usuarioRepositorio;

	

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		var token = this.recoverToken(request);
		var login = tokenServico.validarToken(token);
		
		if(login != null) {
			Usuario usuario = usuarioRepositorio.findByEmail(login).orElseThrow(()->new RuntimeException("Usuario não encontrado !"));
			var authorities = Collections.singletonList(new  SimpleGrantedAuthority("ROLE_USER"));
			var authentication = new UsernamePasswordAuthenticationToken(usuario,null,authorities);
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		 filterChain.doFilter(request, response);
	}

    
	private String recoverToken(HttpServletRequest request) {
		var authHeader = request.getHeader("Authorization");
		if(authHeader == null) return null;
		return authHeader.replace("Bearer ", "");
	}
	
	
}

