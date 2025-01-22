package br.com.delivery.controle;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.delivery.dto.EnderecoDto;
import br.com.delivery.servico.EnderecoServico;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/endereco")
@RequiredArgsConstructor
public class EnderecoControle {
	
	private final EnderecoServico enderecoServico;
	
	
	
	@PostMapping("{clienteId}")
	public ResponseEntity<EnderecoDto>cadastrarEndereco(@RequestBody EnderecoDto enderecoDto,
			@PathVariable("clienteId") Long clienteId){
		var cadastrar = enderecoServico.cadastrarEndereco(enderecoDto, clienteId);
		var uri = ServletUriComponentsBuilder.fromCurrentRequest().path("{/id}")	
		.buildAndExpand(cadastrar.getId()).toUri();
		return ResponseEntity.created(uri).body(new EnderecoDto(cadastrar));
	
	}
	
	@PutMapping
	public ResponseEntity<EnderecoDto>atualizarEndereco(@RequestBody EnderecoDto enderecoDto,@PathVariable Long id){
		var atualizar = enderecoServico.atualizarEndereco(enderecoDto, id);
		return ResponseEntity.ok().body(new EnderecoDto(atualizar));
	}

}
