package com.github.saulmmbp.foroAlura.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

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
		usuarioRepository.deleteById(id);
	}
}
