package com.alysson.cursomc.services;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.alysson.cursomc.domain.Pagamento;
import com.alysson.cursomc.repositories.PagamentoRepository;
import com.alysson.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class PagamentoService {
	
	@Autowired
	private PagamentoRepository pagamentoRepository;	
	
	public Pagamento findById(Integer id){
		Optional<Pagamento> pagamento = pagamentoRepository.findById(id);		
		return pagamento.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Pagamento.class.getName()));
	}
	
	public Pagamento insert(Pagamento pagamento){
		return pagamentoRepository.save(pagamento);
	}
}
