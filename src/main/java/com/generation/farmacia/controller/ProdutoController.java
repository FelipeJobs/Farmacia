package com.generation.farmacia.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.JpaRepositoryNameSpaceHandler;
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

import com.generation.farmacia.model.Categoria;
import com.generation.farmacia.model.Produto;
import com.generation.farmacia.repository.CategoriaRepository;
import com.generation.farmacia.repository.ProdutoRepository;

@RestController
@RequestMapping ("/produtos")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProdutoController {
	@Autowired
	private ProdutoRepository repository;
	
	@Autowired
	private CategoriaRepository categoriarepository;
	
	@GetMapping
	ResponseEntity<List<Produto>>getAll(){
		return ResponseEntity.ok(repository.findAll());
	}
	
	@GetMapping ("/{id}")
	ResponseEntity<Produto>getById(@PathVariable Long id){
		if(repository.existsById(id)) {
			return ResponseEntity.ok().body(repository.getById(id));
		}
		else {
			return ResponseEntity.notFound().build();
		}
	}
	@GetMapping ("/nome/{nome}")
	ResponseEntity<List<Produto>>getByNome(@PathVariable String nome){
		return ResponseEntity.ok(repository.findAllByNomeContainingIgnoreCase(nome));
	}
	
	@PostMapping
	ResponseEntity<Produto>post(@Valid @RequestBody Produto produto){
		if(categoriarepository.existsById(produto.getCategoria().getId())) {
			return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(produto));	
		}
		else {
			return ResponseEntity.notFound().build();
		}
		}

	@PutMapping
	ResponseEntity<Produto>put(@Valid @RequestBody Produto produto){
		if(repository.existsById(produto.getId())) {
			if(categoriarepository.existsById(produto.getCategoria().getId())) {
				return ResponseEntity.ok().body(repository.save(produto));
			}
		}
		else {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.notFound().build();
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

