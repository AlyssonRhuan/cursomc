package com.alysson.cursomc.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.alysson.cursomc.domain.Cliente;
import com.alysson.cursomc.dto.ClienteDTO;
import com.alysson.cursomc.dto.ClienteNewDTO;
import com.alysson.cursomc.services.ClienteService;

@RestController
@RequestMapping(value="/clientes")
public class ClienteResource {
	
	@Autowired
	private ClienteService clienteService;

	@RequestMapping(value="/{id}", method=RequestMethod.GET)	
	public ResponseEntity<Cliente> find(@PathVariable Integer id) {
		Cliente cliente = clienteService.findById(id);		
		return ResponseEntity.ok().body(cliente);
	}

	@RequestMapping(method=RequestMethod.POST)	
	public ResponseEntity<Void> insert(@Valid @RequestBody ClienteNewDTO cliDTO){
		Cliente cli = clienteService.fromDTO(cliDTO);
		cli = clienteService.insert(cli);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(cli.getId())
				.toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Void> update(@PathVariable Integer id,@Valid @RequestBody ClienteDTO cliDTO){
		Cliente cli = clienteService.fromDTO(cliDTO);
		cli.setId(id);
		cli = clienteService.update(cli);
		
		return ResponseEntity.noContent().build();
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)	
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		clienteService.delete(id);
		
		return ResponseEntity.noContent().build();
	}	

	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(method=RequestMethod.GET)	
	public ResponseEntity<List<ClienteDTO>> findAll() {
		List<Cliente> lista = clienteService.findAll();
		List<ClienteDTO> listaDTO = lista.stream().map(obj -> new ClienteDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listaDTO);
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value="/page", method=RequestMethod.GET)	
	public ResponseEntity<Page<ClienteDTO>> findPage(
			@RequestParam(value="page", defaultValue="0") Integer page, 
			@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage, 
			@RequestParam(value="orderBy", defaultValue="nome") String orderBy, 
			@RequestParam(value="direction", defaultValue="ASC") String direction) {
		Page<Cliente> pageCliente = clienteService.findPage(page, linesPerPage, orderBy, direction);
		Page<ClienteDTO> listaPageClienteDTO = pageCliente.map(obj -> new ClienteDTO(obj));
		return ResponseEntity.ok().body(listaPageClienteDTO);
	}

	@RequestMapping(value="/picture", method=RequestMethod.POST)	
	public ResponseEntity<Void> uploadProfilePicture(@RequestParam(name="file") MultipartFile multiPartFile){
		URI uri = clienteService.uploadProfilePicture(multiPartFile);
		return ResponseEntity.created(uri).build();
	}
}
