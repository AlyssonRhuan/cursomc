package com.alysson.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alysson.cursomc.domain.Pedido;
import com.alysson.cursomc.repositories.PedidoRepository;
import com.alysson.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository pedidoRepository;	
	
	public Pedido findById(Integer id){
		Optional<Pedido> pedido = pedidoRepository.findById(id);		
		return pedido.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
	}
}
