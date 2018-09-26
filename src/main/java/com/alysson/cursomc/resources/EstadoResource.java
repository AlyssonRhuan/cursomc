package com.alysson.cursomc.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alysson.cursomc.domain.Estado;
import com.alysson.cursomc.dto.CidadeDTO;
import com.alysson.cursomc.dto.EstadoDTO;
import com.alysson.cursomc.services.EstadoService;

@RestController
@RequestMapping(value="/estados")
public class EstadoResource {
	
	@Autowired
	private EstadoService estadoService;
	
	@Autowired
	private CidadeResource cidadeResource;

	@RequestMapping(method=RequestMethod.GET)	
	public ResponseEntity<List<EstadoDTO>> findAll() {
		List<Estado> lista = estadoService.findAll();
		List<EstadoDTO> listaDTO = lista.stream().map(obj -> new EstadoDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listaDTO);
	}

	@RequestMapping(value="/{estado_id}/cidades", method=RequestMethod.GET)	
	public ResponseEntity<List<CidadeDTO>> findAllByEstado(@PathVariable Integer estado_id) {
		return cidadeResource.findAllByEstado(estado_id);
	}
	
	@RequestMapping(value="/{estado_id}", method=RequestMethod.GET)	
	public ResponseEntity<Estado> find(@PathVariable Integer estado_id) {
		Estado estado = estadoService.findById(estado_id);		
		return ResponseEntity.ok().body(estado);
	}
}
