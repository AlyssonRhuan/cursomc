package com.alysson.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.alysson.cursomc.domain.enums.TipoCliente;
import com.alysson.cursomc.dto.ClienteNewDTO;
import com.alysson.cursomc.resources.exceptions.FieldMessage;
import com.alysson.cursomc.services.validation.utils.BR; 
 
public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> { 
 
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
    	
        //
        for (FieldMessage e : list) {             
        	context.disableDefaultConstraintViolation();             
        	context.buildConstraintViolationWithTemplate(e.getMessage())             
        	.addPropertyNode(e.getFieldName()).addConstraintViolation();         
        	}         
        
        return list.isEmpty();     
    } 
 }