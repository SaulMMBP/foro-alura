package com.github.saulmmbp.foroAlura.service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import com.github.saulmmbp.foroAlura.controller.UsuarioController;
import com.github.saulmmbp.foroAlura.dao.UsuarioRepository;
import com.github.saulmmbp.foroAlura.dto.request.*;
import com.github.saulmmbp.foroAlura.dto.response.UsuarioResponse;
import com.github.saulmmbp.foroAlura.entity.Usuario;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UsuarioService {

	private UsuarioRepository usuarioRepository;

	public UsuarioService(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}
	
	public List<UsuarioResponse> findAll() {
		return usuarioRepository.findAll().stream()
				.map(Usuario::toResponse)
				.collect(Collectors.toList());
	}
	
	public Page<UsuarioResponse> findAllPageable(Pageable pageable) {
		return usuarioRepository.findAll(pageable).map(usuario -> usuario.toResponse()
					.add(linkTo(methodOn(UsuarioController.class).getUsuarios(pageable)).withRel("usuarios")));
	}
	
	public UsuarioResponse findById(Long id) {
		return usuarioRepository.findById(id).orElseThrow().toResponse();
	}
	
	public UsuarioResponse save(UsuarioPostRequest usuario) {
		return usuarioRepository.save(usuario.toEntity()).toResponse();
	}
	
	public UsuarioResponse update(UsuarioPutRequest usuarioReq) {
		Usuario usuario = usuarioRepository.findById(usuarioReq.id()).orElseThrow();
		usuario.update(usuarioReq);
		return usuarioRepository.save(usuario).toResponse();
	}
	
	public void delete(Long id) {
		if(!usuarioRepository.existsById(id)) {
			throw new NoSuchElementException();
		}
		usuarioRepository.deleteById(id);
	}
}
