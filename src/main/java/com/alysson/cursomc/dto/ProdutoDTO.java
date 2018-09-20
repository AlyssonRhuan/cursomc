package com.alysson.cursomc.dto;

import java.io.Serializable;

import com.alysson.cursomc.domain.Produto;
import com.alysson.cursomc.services.validation.ClienteUpdate;

@ClienteUpdate
public class ProdutoDTO  implements Serializable{
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String nome;
	private Double preco;
	
	public ProdutoDTO() {
		
	}

	public ProdutoDTO(Produto p) {
		super();
		this.id = p.getId();
		this.nome = p.getNome();
		this.preco = p.getPreco();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}
}
