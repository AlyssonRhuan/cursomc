package com.alysson.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import com.alysson.cursomc.domain.Cliente;
import com.alysson.cursomc.dto.ClienteDTO;
import com.alysson.cursomc.repositories.ClienteRepository;
import com.alysson.cursomc.resources.exceptions.FieldMessage;
 
public class ClienteUpdateValidator implements ConstraintValidator<ClienteUpdate, ClienteDTO> { 
	
	@Autowired
	private HttpServletRequest httpServletRequest;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
    @Override 
    public void initialize(ClienteUpdate ann) {     
    	}       
    
    @Override     
    public boolean isValid(ClienteDTO objDto, ConstraintValidatorContext context) { 
    	
    	@SuppressWarnings("unchecked")
		Map<String, String> map = (Map<String, String>) httpServletRequest.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE); 
    	Integer uriId = Integer.parseInt(map.get("id"));
    	
        List<FieldMessage> list = new ArrayList<>();                  
        // TESTS
    	
        Cliente cli = clienteRepository.findByEmail(objDto.getEmail());
    	if(cli != null && !cli.getId().equals(uriId))
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