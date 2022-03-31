package com.generation.farmacia.controller;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generation.farmacia.repository.CategoriaRepository;
import com.generation.farmacia.model.Categoria;
@RestController
@RequestMapping ("/categorias")
@CrossOrigin(origins = "*", allowedHeaders = "*")

public class CategoriaController {
	
	@Autowired 
	private CategoriaRepository repository;
	
	@GetMapping
	ResponseEntity<List<Categoria>>getAll(){
		return ResponseEntity.ok(repository.findAll());
		
	}
	
	@GetMapping ("/{id}")
	ResponseEntity<Optional<Categoria>> getById(@PathVariable Long id){
		if(repository.existsById(id)) {
			return ResponseEntity.ok().body(repository.findById(id));
		}
		else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@GetMapping ("/nome/{nome}")
	ResponseEntity<List<Categoria>> getByNome(@PathVariable String nome){
		return ResponseEntity.ok(repository.findAllByNomeContainingIgnoreCase(nome));
	}
	
	@PostMapping
	ResponseEntity<Categoria>post(@Valid @RequestBody Categoria categoria){
		return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(categoria));
	}
	
	@PutMapping
	ResponseEntity<Categoria>put(@Valid @RequestBody Categoria categoria){
		if(repository.existsById(categoria.getId())) {
			return ResponseEntity.ok().body(repository.save(categoria));
		}
		else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@DeleteMapping ("/{id}")
	ResponseEntity<Categoria> deletar(@PathVariable Long id){
		if(repository.existsById(id)) {
			repository.deleteById(id);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
		else {
			return ResponseEntity.notFound().build();
		}
	}

}

