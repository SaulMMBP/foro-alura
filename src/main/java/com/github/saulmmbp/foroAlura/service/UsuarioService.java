package com.github.saulmmbp.foroAlura.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.github.saulmmbp.foroAlura.dao.UsuarioRepository;
import com.github.saulmmbp.foroAlura.dto.UsuarioDto;
import com.github.saulmmbp.foroAlura.entity.Usuario;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UsuarioService {

	private UsuarioRepository usuarioRepository;

	public UsuarioService(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}
	
	public List<UsuarioDto> findAll() {
		return usuarioRepository.findAll().stream()
				.map(Usuario::toDto)
				.collect(Collectors.toList());
	}
	
	public UsuarioDto findById(Long id) {
		return usuarioRepository.findById(id).orElseThrow().toDto();
	}
	
	public UsuarioDto save(UsuarioDto usuarioDto) {
		return usuarioRepository.save(usuarioDto.toEntity()).toDto();
	}
	
	public UsuarioDto update(UsuarioDto usuarioDto, Long id) {
		Usuario usuario = usuarioRepository.findById(id).orElseThrow();
		usuario.setNombre(usuarioDto.nombre());
		usuario.setContrasena(usuarioDto.contrasena());
		usuario.setEmail(usuarioDto.email());
		return usuarioRepository.save(usuario).toDto();
	}
	
	public void delete(Long id) {
		usuarioRepository.deleteById(id);
	}
}
