package com.alysson.cursomc.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alysson.cursomc.domain.Cidade;
import com.alysson.cursomc.dto.CidadeDTO;
import com.alysson.cursomc.services.CidadeService;

@RestController
@RequestMapping(value="/cidades")
public class CidadeResource {
	
	@Autowired
	private CidadeService cidadeService;

	@RequestMapping(method=RequestMethod.GET)	
	public ResponseEntity<List<CidadeDTO>> findAll() {
		List<Cidade> lista = cidadeService.findAll();
		List<CidadeDTO> listaDTO = lista.stream().map(obj -> new CidadeDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listaDTO);
	}

	public ResponseEntity<List<CidadeDTO>> findAllByEstado(Integer estadoId) {
		List<Cidade> lista = cidadeService.findByEstado(estadoId);
		List<CidadeDTO> listaDTO = lista.stream().map(obj -> new CidadeDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listaDTO);
	}
}
