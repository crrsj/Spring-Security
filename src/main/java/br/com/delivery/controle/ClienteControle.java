package br.com.delivery.controle;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.delivery.dto.AtualizarDto;
import br.com.delivery.dto.BuscarClienteDto;
import br.com.delivery.dto.ClienteDto;
import br.com.delivery.servico.ClienteServico;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/delivery")
@RequiredArgsConstructor
public class ClienteControle {

	private final ClienteServico clienteServico;
	
	@PostMapping
	public ResponseEntity<ClienteDto>cadastrarCliente(@RequestBody ClienteDto clienteDto){
		var cadastrar = clienteServico.cadastrarCliente(clienteDto);
		var uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").
			buildAndExpand(cadastrar.getId()).toUri();	
		return ResponseEntity.created(uri).body(new ClienteDto(cadastrar));
	}
	
	
	@GetMapping
	public ResponseEntity<List<BuscarClienteDto>>buscarClientes(){
		var busca = clienteServico.buscarClientes();
		return ResponseEntity.ok().body(busca);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<BuscarClienteDto>buscarPorId(@PathVariable Long id){
		var buscaId = clienteServico.buscarPorId(id);
		return ResponseEntity.ok().body(new BuscarClienteDto(buscaId));
	}
	
	@GetMapping("nome")
	public ResponseEntity<List<BuscarClienteDto>>buscarPorNome(@RequestParam("nome")String nome){
	var buscaNome = clienteServico.buscarPorNome(nome);
	return ResponseEntity.ok().body(buscaNome.stream().map(BuscarClienteDto::new).toList());
	
	
		
	}
	
	@PutMapping("{id}")
	public ResponseEntity<AtualizarDto>atualizarCliente(@RequestBody AtualizarDto atualizarDto,@PathVariable Long id){		
		var atualizar = clienteServico.atualizarCliente(atualizarDto, id);
		return ResponseEntity.ok().body(new AtualizarDto(atualizar));
	}
	
	
	
	@DeleteMapping("{id}")
	public ResponseEntity<Void>excluirCliente(@PathVariable Long id){
		clienteServico.excluirCliente(id);
		return ResponseEntity.noContent().build();
	}
}
	
