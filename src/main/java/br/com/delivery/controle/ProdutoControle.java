package br.com.delivery.controle;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.delivery.dto.ProdutoDto;
import br.com.delivery.servico.ProdutoServico;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/produto")
@RequiredArgsConstructor
public class ProdutoControle {
	
	private final ProdutoServico produtoServico;
	
	
	@PostMapping("{restauranteId}")
	public ResponseEntity<ProdutoDto>criarProduto(@RequestBody ProdutoDto produtoDto,
			@PathVariable("restauranteId")Long restauranteId){
	  	
	  var criar = produtoServico.cadastrarProduto(produtoDto, restauranteId); 
	  var uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").
		buildAndExpand(criar.getId()).toUri();
	  return ResponseEntity.created(uri).body(new ProdutoDto(criar));
		
	}

	@GetMapping("{id}")
	public ResponseEntity<ProdutoDto>buscarPorId(@PathVariable Long id){
		var buscaId = produtoServico.buscarPorId(id);
		return ResponseEntity.ok().body(new ProdutoDto(buscaId));
	}
	
	@GetMapping
	public ResponseEntity<List<ProdutoDto>>buscarProdutos(){
		var busca = produtoServico.buscarProdutos();
		return ResponseEntity.ok().body(busca);
	}
	
	
	@DeleteMapping("{id}")
	public ResponseEntity<Void>excluirProdutos(@PathVariable Long id){
		produtoServico.excluirProduto(id);
		return ResponseEntity.noContent().build();
	}
}
