package com.alysson.cursomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alysson.cursomc.domain.Cidade;
import com.alysson.cursomc.repositories.CidadeRepository;

@Service
public class CidadeService {
	
	@Autowired
	public CidadeRepository cidadeRepository;	

	public List<Cidade> findAll() {
		return cidadeRepository.findAll();
	}
	
	public List<Cidade> findByEstado(Integer estadoID){
		List<Cidade> cidades = cidadeRepository.findCidade(estadoID);
		return cidades;
	}
}
