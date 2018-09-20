package com.alysson.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.alysson.cursomc.domain.Cidade;
import com.alysson.cursomc.domain.Cliente;
import com.alysson.cursomc.domain.Endereco;
import com.alysson.cursomc.domain.enums.TipoCliente;
import com.alysson.cursomc.dto.ClienteDTO;
import com.alysson.cursomc.dto.ClienteNewDTO;
import com.alysson.cursomc.repositories.ClienteRepository;
import com.alysson.cursomc.repositories.EnderecoRepository;
import com.alysson.cursomc.services.exceptions.DataIntegrityException;
import com.alysson.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository clienteRepository;	
	
	@Autowired
	private EnderecoRepository enderecoRepository;	
	
	public Cliente findById(Integer id){
		Optional<Cliente> cliente = clienteRepository.findById(id);		
		return cliente.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}
	
	public Cliente insert(Cliente cli) {
		cli.setId(null);
		cli = clienteRepository.save(cli);
		enderecoRepository.saveAll(cli.getEnderecos());
		return cli;
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
	
	public Cliente fromDTO(ClienteNewDTO clienteNewDTO) {
		Cliente cliente = new Cliente(
				null, 
				clienteNewDTO.getNome(), 
				clienteNewDTO.getEmail(), 
				clienteNewDTO.getCpfOuCnpj(), 
				TipoCliente.toEnum(clienteNewDTO.getTipo()));
		
		Cidade cidade = new Cidade(clienteNewDTO.getCidadeId(), null, null);
		
		Endereco endereco = new Endereco(
				null, 
				clienteNewDTO.getLogradouro(), 
				clienteNewDTO.getNumero(), 
				clienteNewDTO.getComplemento(), 
				clienteNewDTO.getBairro(), 
				clienteNewDTO.getCep(), 
				cliente, 
				cidade);
		
		cliente.getEnderecos().add(endereco);		
		cliente.getTelefones().add(clienteNewDTO.getTelefone1());
		
		if(clienteNewDTO.getTelefone2() != null)
			cliente.getTelefones().add(clienteNewDTO.getTelefone2());
		
		if(clienteNewDTO.getTelefone3() != null)
			cliente.getTelefones().add(clienteNewDTO.getTelefone3());
		
		return cliente;
	}
	
	private void updateData(Cliente newClient, Cliente cli) {
		newClient.setNome(cli.getNome());
		newClient.setEmail(cli.getEmail());
	}
}
