package com.alysson.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.alysson.cursomc.domain.Cliente;
import com.alysson.cursomc.dto.ClienteDTO;
import com.alysson.cursomc.repositories.ClienteRepository;
import com.alysson.cursomc.services.exceptions.DataIntegrityException;
import com.alysson.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository clienteRepository;	
	
	public Cliente findById(Integer id){
		Optional<Cliente> cliente = clienteRepository.findById(id);		
		return cliente.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}
	
	public Cliente update(Cliente cli) {
		Cliente newClient = findById(cli.getId());
		updateData(newClient, cli);
		return clienteRepository.save(newClient);
	}
	
	public void delete(Integer id) {
		findById(id);
		
		try {
			clienteRepository.deleteById(id);	
		} 
		catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possivel excluir um cliente, pois há entidades relacionadas.");
		}			
	}
	
	public List<Cliente> findAll(){
		return clienteRepository.findAll();
	}
	
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageResquest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return clienteRepository.findAll(pageResquest);
	}
	
	public Cliente fromDTO(ClienteDTO clienteDTO) {
		return new Cliente(clienteDTO.getId(), clienteDTO.getNome(), clienteDTO.getEmail(), null, null);
	}
	
	private void updateData(Cliente newClient, Cliente cli) {
		newClient.setNome(cli.getNome());
		newClient.setEmail(cli.getEmail());
	}
}
