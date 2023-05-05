package com.github.saulmmbp.foroAlura.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.github.saulmmbp.foroAlura.dao.*;
import com.github.saulmmbp.foroAlura.dto.*;
import com.github.saulmmbp.foroAlura.entity.*;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class TopicoService {

	private TopicoRepository topicoRepository;
	private UsuarioRepository usuarioRepository;
	private CursoRepository cursoRepository;
	
	public TopicoService(TopicoRepository topicoRepository, 
			UsuarioRepository usuarioRepository,
			CursoRepository cursoRepository) {
		this.topicoRepository = topicoRepository;
		this.usuarioRepository = usuarioRepository;
		this.cursoRepository = cursoRepository;
	}
	
	public List<TopicoResponse> findAll() {
		return this.topicoRepository.findAll().stream()
				.map(Topico::toResponse)
				.collect(Collectors.toList());
	}
	
	public TopicoResponse findById(Long id) {
		return this.topicoRepository.findById(id).orElseThrow().toResponse();
	}
	
	public TopicoResponse save(TopicoPostRequest topicoReq) {
		Usuario autor = usuarioRepository.findById(topicoReq.autor_id()).orElseThrow();
		Curso curso = cursoRepository.findById(topicoReq.curso_id()).orElseThrow();
		Topico topico = topicoReq.toEntity();
		topico.setAutor(autor);
		topico.setCurso(curso);
		return this.topicoRepository.save(topico).toResponse();
	}
	
	public TopicoResponse update(TopicoPutRequest topicoReq) {
		Topico topico = this.topicoRepository.findById(topicoReq.id()).orElseThrow();
		topico.update(topicoReq);
		return this.topicoRepository.save(topico).toResponse();
	}
	
	public void delete(Long id) {
		this.topicoRepository.deleteById(id);
	}
}
