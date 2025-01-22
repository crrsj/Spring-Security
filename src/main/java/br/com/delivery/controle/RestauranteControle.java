package br.com.delivery.controle;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.delivery.dto.RestauranteDto;
import br.com.delivery.servico.RestauranteServico;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/restaurante")
@RequiredArgsConstructor
public class RestauranteControle {

	private final RestauranteServico restauranteServico;
	
	
	@PostMapping
	public ResponseEntity<RestauranteDto>cadastrarRestaurante(@RequestBody RestauranteDto restauranteDto){
		var cadastrar = restauranteServico.cadastrarRestaurante(restauranteDto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("{/id}").
		buildAndExpand(cadastrar.getId()).toUri();
		return ResponseEntity.created(uri).body(new RestauranteDto(cadastrar));
	}
	
	
	@GetMapping
	public ResponseEntity<List<RestauranteDto>>buscarRestaurantes(){
		var buscar = restauranteServico.buscarRestaurantes();
		return ResponseEntity.ok().body(buscar);
	}
	
	
	@GetMapping("{id}")
	public ResponseEntity<RestauranteDto>buscarPorId(@PathVariable Long id){
		var buscarId = restauranteServico.buscarPorId(id);
		return ResponseEntity.ok().body(new RestauranteDto(buscarId));
	}
	
	
	@PutMapping("{id}")
	public ResponseEntity<RestauranteDto>atualizarRestaurante(@RequestBody RestauranteDto restauranteDto,@PathVariable Long id){
		var atualizar = restauranteServico.atualizarRestaurante(restauranteDto, id);
		return ResponseEntity.ok(new RestauranteDto(atualizar));
	}
}
