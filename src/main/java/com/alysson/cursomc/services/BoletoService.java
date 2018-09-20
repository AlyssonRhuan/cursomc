package com.alysson.cursomc.services;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.alysson.cursomc.domain.PagamentoComBoleto;

@Service
public class BoletoService {

	public void preencherPagamentoComBoleto(PagamentoComBoleto pagto, Date instante){
		Calendar date = Calendar.getInstance();
		date.setTime(instante);
		date.add(Calendar.DAY_OF_MONTH, 7);
		pagto.setDataVencimento(date.getTime());
	}
}
