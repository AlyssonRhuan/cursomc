package com.alysson.cursomc.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alysson.cursomc.domain.ItemPedido;
import com.alysson.cursomc.domain.PagamentoComBoleto;
import com.alysson.cursomc.domain.Pedido;
import com.alysson.cursomc.domain.enums.EstadoPagamento;
import com.alysson.cursomc.repositories.ItemPedidoRepository;
import com.alysson.cursomc.repositories.PagamentoRepository;
import com.alysson.cursomc.repositories.PedidoRepository;
import com.alysson.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository pedidoRepository;	
	
	@Autowired
	private BoletoService boletoService;	
	
	@Autowired
	private PagamentoRepository pagamentoRepository;	
	
	@Autowired
	private ProdutoService produtoService;	
	
	@Autowired
	private ClienteService clienteService;	
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;	
	
	@Autowired
	private EmailService emailService;	
	
	public Pedido findById(Integer id){
		Optional<Pedido> pedido = pedidoRepository.findById(id);		
		return pedido.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
	}
	
	public Pedido insert(Pedido pedido){
		pedido.setId(null);
		pedido.setInstante(new Date());
		pedido.setCliente(clienteService.findById(pedido.getCliente().getId()));
		pedido.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		pedido.getPagamento().setPedido(pedido);
		
		if(pedido.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto) pedido.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagto, pedido.getInstante());
		}
		
		pagamentoRepository.save(pedido.getPagamento());
		
		for(ItemPedido ip : pedido.getItens()){
			ip.setDesconto(0.0);
			ip.setProduto(produtoService.findById(pedido.getId()));
			ip.setPreco(ip.getProduto().getPreco());
			ip.setPedido(pedido);
		}	
		
		itemPedidoRepository.saveAll(pedido.getItens());
		
//		emailService.sendOrderConfirmationEmail(pedido);
		emailService.sendOrderConfirmationHtmlEmail(pedido);
		
		return pedido;
	}
}
