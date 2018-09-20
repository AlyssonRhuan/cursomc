package com.alysson.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.alysson.cursomc.domain.enums.TipoCliente;
import com.alysson.cursomc.dto.ClienteNewDTO;
import com.alysson.cursomc.repositories.ClienteRepository;
import com.alysson.cursomc.resources.exceptions.FieldMessage;
import com.alysson.cursomc.services.validation.utils.BR; 
 
public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> { 
	
	@Autowired
	private ClienteRepository clienteRepository;
	
    @Override 
    public void initialize(ClienteInsert ann) {     
    	}       
    
    @Override     
    public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) { 
    	 
        List<FieldMessage> list = new ArrayList<>();                  
        // TESTS
        
    	if(objDto.getTipo().equals(TipoCliente.PESSOAFISICA.getCod()) && !BR.isValidCPF(objDto.getCpfOuCnpj()))
    		list.add(new FieldMessage("cpfOuCnpj", "CPF inválido"));

    	if(objDto.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCod()) && !BR.isValidCPF(objDto.getCpfOuCnpj()))
    		list.add(new FieldMessage("cpfOuCnpj", "CNPJ inválido"));
    	
    	if(clienteRepository.findByEmail(objDto.getEmail()) != null)
    		list.add(new FieldMessage("email", "Email já existente"));
    	
        //
        for (FieldMessage e : list) {             
        	context.disableDefaultConstraintViolation();             
        	context.buildConstraintViolationWithTemplate(e.getMessage())             
        	.addPropertyNode(e.getFieldName()).addConstraintViolation();         
        	}         
        
        return list.isEmpty();     
    } 
 } 