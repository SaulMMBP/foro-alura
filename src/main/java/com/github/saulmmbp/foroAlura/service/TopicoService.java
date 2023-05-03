package com.github.saulmmbp.foroAlura.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.github.saulmmbp.foroAlura.dao.TopicoRepository;
import com.github.saulmmbp.foroAlura.dto.TopicoDto;
import com.github.saulmmbp.foroAlura.entity.Topico;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class TopicoService {
	
	private TopicoRepository topicoRepository;
	
	public TopicoService(TopicoRepository topicoRepository) {
		this.topicoRepository = topicoRepository;
	}
	
	public List<TopicoDto> findAll() {
		return this.topicoRepository.findAll().stream()
				.map(topico -> topico.toDto())
				.collect(Collectors.toList());
	}
	
	public TopicoDto findById(Long id) {
		return this.topicoRepository.findById(id).orElseThrow().toDto();
	}
	
	public TopicoDto save(TopicoDto topicoDto) {
		return this.topicoRepository.save(topicoDto.toEntity()).toDto();
	}
	
	public TopicoDto update(TopicoDto topicoDto, Long id) {
		Topico updatedTopico = this.topicoRepository.findById(id).orElseThrow();
		updatedTopico.setTitulo(topicoDto.titulo());
		updatedTopico.setMensaje(topicoDto.mensaje());
		updatedTopico.setEstado(topicoDto.estado());
		updatedTopico.setAutor(topicoDto.autor().toEntity());
		updatedTopico.setCurso(topicoDto.curso().toEntity());
		
		return this.topicoRepository.save(updatedTopico).toDto();
	}
	
	public void delete(Long id) {
		this.topicoRepository.deleteById(id);
	}
	
}
