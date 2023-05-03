package com.github.saulmmbp.foroAlura.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.github.saulmmbp.foroAlura.dao.UsuarioRepository;
import com.github.saulmmbp.foroAlura.dto.UsuarioDto;
import com.github.saulmmbp.foroAlura.entity.Usuario;

@Service
public class UsuarioService {

	private UsuarioRepository usuarioRepository;

	public UsuarioService(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}
	
	public List<UsuarioDto> findAll() {
		return usuarioRepository.findAll().stream().map(Usuario::toDto).toList();
	}
	
	public UsuarioDto findById(Long id) {
		return usuarioRepository.findById(id).orElseThrow().toDto();
	}
	
}
