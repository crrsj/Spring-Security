package br.com.delivery.servico.seguranca;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

import br.com.delivery.entidade.Usuario;

@Service
public class TokenServico {
	
	@Value("${api.seguranca.token.secreto}")
	private String secreto;
	
	public String gerarToken(Usuario usuario) {
		
		try {
			
			Algorithm algorithm = Algorithm.HMAC256(secreto);
			
			String token = JWT.create()
			.withIssuer("login-auth-api")
			.withSubject(usuario.getEmail())
			.withExpiresAt(this.gerarTempoExpiracao())
			.sign(algorithm);
			return token;
		} catch (JWTCreationException ex) {
			throw new RuntimeException("Erro ao autenticar");
		}
	}
	
	public String validarToken(String token) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(secreto);
			return JWT.require(algorithm)
			.withIssuer("login-auth-api")
			.build()
			.verify(token)
			.getSubject();
		} catch (JWTVerificationException ex ) {
			return null;
		}
	}

	private Instant gerarTempoExpiracao() {
		return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-0300"));
	}
}
