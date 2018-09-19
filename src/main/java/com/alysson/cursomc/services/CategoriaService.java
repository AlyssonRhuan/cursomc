package com.alysson.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alysson.cursomc.domain.Categoria;
import com.alysson.cursomc.repositories.CategoriaRepository;
import com.alysson.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository categoriaRepository;	
	
	public Categoria buscar(Integer id){
		Optional<Categoria> categoria = categoriaRepository.findById(id);		
		return categoria.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
	}
	
	public Categoria insert(Categoria cat) {
		cat.setId(null);
		return categoriaRepository.save(cat);
	}
}
