package br.com.delivery.controle;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.delivery.dto.LoginRequisicao;
import br.com.delivery.dto.RegistroDto;
import br.com.delivery.dto.RespostaDto;
import br.com.delivery.entidade.Usuario;
import br.com.delivery.repositorio.UsuarioRepositorio;
import br.com.delivery.servico.seguranca.TokenServico;


import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class LoginControle {

	private final UsuarioRepositorio repositorio;
	private final PasswordEncoder encoder;
	private final TokenServico servico;
	
	
	
	@PostMapping("/login")
	public ResponseEntity<?> fazerLogin(@RequestBody LoginRequisicao requisicao){
		Usuario usuario = this.repositorio.findByEmail(requisicao.email()).
				orElseThrow(()-> new RuntimeException("usuário não encontrado"));
		if(encoder.matches(requisicao.senha(),usuario.getSenha())){
			String token = this.servico.gerarToken(usuario);
			return ResponseEntity.ok(new RespostaDto(usuario.getNome(),token));
		}
		
		return ResponseEntity.badRequest().build();
		
	}
	
	@PostMapping("/register")	
	public ResponseEntity<?> registro(@RequestBody RegistroDto requisicao) {
	Optional<Usuario> usuario = this.repositorio.findByEmail(requisicao.email());
		
	if(usuario.isEmpty()) {
	Usuario novoUsuario = new Usuario();	
	novoUsuario.setNome(requisicao.nome());
	novoUsuario.setEmail(requisicao.email());
	novoUsuario.setSenha(encoder.encode(requisicao.senha()));
	this.repositorio.save(novoUsuario);
	String token = this.servico.gerarToken(novoUsuario);
	return ResponseEntity.ok(new RespostaDto(novoUsuario.getNome(),token));
	
	 }
	
	return ResponseEntity.badRequest().build();
	}

}
