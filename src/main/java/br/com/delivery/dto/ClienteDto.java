package br.com.delivery.dto;

import java.util.Date;
import java.util.List;

import br.com.delivery.entidade.Cliente;
import br.com.delivery.entidade.Endereco;
import br.com.delivery.entidade.Pedido;
import br.com.delivery.enums.Tipo;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ClienteDto {

	@NotBlank(message = "não pode estar em branco !")
	private String nome;
	@NotBlank(message = "não pode estar em branco !")
	private String fone;
	@Email
	private String email;
	@NotBlank(message = "não pode estar em branco !")
	private String senha;	
	private Tipo tipo;	
	private Date dataCriacao;	
	private Date dataAtualizacao;
	private List<Pedido>pedido;
	private List<Endereco>endereco;
	
	public ClienteDto(Cliente cadastrar) {
		this.nome = cadastrar.getNome();
		this.fone = cadastrar.getFone();		
		this.tipo = cadastrar.getTipo();
		this.dataCriacao = cadastrar.getDataCriacao();
		this.dataAtualizacao = cadastrar.getDataAtualizacao();
		this.pedido = cadastrar.getPedido();
		this.endereco = cadastrar.getEndereco();
	}
}
