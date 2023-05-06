package com.github.saulmmbp.foroAlura.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.github.saulmmbp.foroAlura.dao.CursoRepository;
import com.github.saulmmbp.foroAlura.dto.request.*;
import com.github.saulmmbp.foroAlura.dto.response.CursoResponse;
import com.github.saulmmbp.foroAlura.entity.Curso;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CursoService {

	private CursoRepository cursoRepository;

	public CursoService(CursoRepository cursoRepository) {
		this.cursoRepository = cursoRepository;
	}
	
	public List<CursoResponse> findAll() {
		return cursoRepository.findAll().stream()
				.map(Curso::toResponse)
				.collect(Collectors.toList());
	}
	
	public CursoResponse findById(Long id) {
		return cursoRepository.findById(id).orElseThrow().toResponse();
	}
	
	public CursoResponse save(CursoPostRequest curso) {
		return cursoRepository.save(curso.toEntity()).toResponse();
	}
	
	public CursoResponse update(CursoPutRequest cursoReq) {
		Curso curso = cursoRepository.findById(cursoReq.id()).orElseThrow();
		curso.update(cursoReq);
		return cursoRepository.save(curso).toResponse();
	}
	
	public void delete(Long id) {
		cursoRepository.deleteById(id);
	}
}
