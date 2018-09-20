package com.alysson.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.alysson.cursomc.domain.Categoria;
import com.alysson.cursomc.dto.CategoriaDTO;
import com.alysson.cursomc.repositories.CategoriaRepository;
import com.alysson.cursomc.services.exceptions.DataIntegrityException;
import com.alysson.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository categoriaRepository;	
	
	public Categoria findById(Integer id){
		Optional<Categoria> categoria = categoriaRepository.findById(id);		
		return categoria.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
	}
	
	public Categoria insert(Categoria cat) {
		cat.setId(null);
		return categoriaRepository.save(cat);
	}
	
	public Categoria update(Categoria cat) {
		Categoria newCategoria = findById(cat.getId());
		updateData(newCategoria, cat);
		return categoriaRepository.save(newCategoria);
	}
	
	public void delete(Integer id) {
		findById(id);
		
		try {
			categoriaRepository.deleteById(id);	
		} 
		catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possivel excluir uma categoria que possui produtos.");
		}			
	}
	
	public List<Categoria> findAll(){
		return categoriaRepository.findAll();
	}
	
	public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageResquest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return categoriaRepository.findAll(pageResquest);
	}
	
	public Categoria fromDTO(CategoriaDTO categoriaDTO) {
		return new Categoria(categoriaDTO.getId(), categoriaDTO.getNome());
	}
	
	private void updateData(Categoria newCategoria, Categoria cat) {
		newCategoria.setNome(cat.getNome());
	}
}
