package com.alysson.cursomc.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.alysson.cursomc.domain.Cliente;
import com.alysson.cursomc.repositories.ClienteRepository;
import com.alysson.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class AuthService {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private EmailService emailService;
	
	private Random random = new Random();
	
	public void sendNewPassword(String email) {
		Cliente cliente = clienteRepository.findByEmail(email);
		if(cliente == null)
			throw new ObjectNotFoundException("Email não encontrado!");
		
		String newPass = newPassword();
		cliente.setSenha(bCryptPasswordEncoder.encode(newPass));
		clienteRepository.save(cliente);
		
		emailService.sendNewPasswordEmail(cliente, newPass);
	}

	private String newPassword() {
		char[] vet = new char[10];
		
		 for(int i = 0; i < 10; i++) {
			 vet[i] = randomChar();
		 }
		
		return vet.toString();
	}

	private char randomChar() {
		int opt = random.nextInt(3);
		
		if(opt == 0) { //GERA UM DIGITO
			return (char) (random.nextInt(10) + 48);
		}
		else if(opt == 1) { //GERA UM LETRA MAIUSCULA
			return (char) (random.nextInt(26) + 65);
		}
		else { //GERA UM MINUSCULA
			return (char) (random.nextInt(26) + 97);
		}
	}
}
