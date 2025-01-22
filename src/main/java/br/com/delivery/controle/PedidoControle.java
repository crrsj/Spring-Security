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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.delivery.dto.PedidoDto;
import br.com.delivery.servico.PedidoServico;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/pedido")
@RequiredArgsConstructor
public class PedidoControle {

	private final PedidoServico pedidoServico;
	
	
	@PostMapping("{clienteId}")
	public ResponseEntity<PedidoDto>criarPedido(@RequestBody PedidoDto pedidoDto,@PathVariable("clienteId")Long clienteId){
		var criarPedido = pedidoServico.criarpedido(pedidoDto, clienteId);
		var uri = ServletUriComponentsBuilder.fromCurrentRequest().path("{/id}").
		buildAndExpand(criarPedido.getId()).toUri();
		return ResponseEntity.created(uri).body(new PedidoDto(criarPedido));
		
	}
	
	@GetMapping
	public ResponseEntity<List<PedidoDto>>buscarPedidos(){
		var buscar = pedidoServico.buscarPedidos();
		return ResponseEntity.ok().body(buscar);
	}
	
	
	@GetMapping("{id}")
	public ResponseEntity<PedidoDto>buscarPorId(@PathVariable Long id){
		var buscarId = pedidoServico.buscarPorId(id);
		return ResponseEntity.ok().body(new PedidoDto(buscarId));
	}
	
	
	@PutMapping("{id}")
	public ResponseEntity<PedidoDto>atualizarPedido(@RequestBody PedidoDto pedidoDto,@PathVariable Long id){
		var atualize = pedidoServico.atualizarPedido(pedidoDto, id);
		return ResponseEntity.ok().body(new PedidoDto(atualize));
	}
	
	
	@DeleteMapping
	public ResponseEntity<Void>excluirPedido(@PathVariable Long id){
		pedidoServico.excluirPedidos(id);
		return ResponseEntity.noContent().build();
	}
}
