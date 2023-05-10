package com.github.saulmmbp.foroAlura.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import com.github.saulmmbp.foroAlura.dao.*;
import com.github.saulmmbp.foroAlura.dto.request.*;
import com.github.saulmmbp.foroAlura.dto.response.TopicoResponse;
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
		return topicoRepository.findAll().stream()
				.map(Topico::toResponse)
				.collect(Collectors.toList());
	}
	
	public Page<TopicoResponse> findAllPageable(int page, int size, String sortBy, String orderBy) {
		Sort sort = orderBy.equalsIgnoreCase(Sort.Direction.ASC.name()) ? 
				Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
		Pageable pageable = PageRequest.of(page, size, sort);
		return topicoRepository.findAll(pageable).map(Topico::toResponse);
	}
	
	public TopicoResponse findById(Long id) {
		return topicoRepository.findById(id).orElseThrow().toResponse();
	}
	
	public TopicoResponse save(TopicoPostRequest topicoReq) {
		Usuario autor = usuarioRepository.findById(topicoReq.autor_id()).orElseThrow();
		Curso curso = cursoRepository.findById(topicoReq.curso_id()).orElseThrow();
		Topico topico = topicoReq.toEntity();
		topico.setAutor(autor);
		topico.setCurso(curso);
		return topicoRepository.save(topico).toResponse();
	}
	
	public TopicoResponse update(TopicoPutRequest topicoReq) {
		Usuario autor = usuarioRepository.findById(topicoReq.autorId()).orElseThrow();
		Topico topico = topicoRepository.findByIdAndAutor(topicoReq.id(), autor).orElseThrow();
		topico.update(topicoReq);
		return topicoRepository.save(topico).toResponse();
	}
	
	public void delete(Long id) {
		topicoRepository.findById(id).orElseThrow();
		topicoRepository.deleteById(id);
	}
}
