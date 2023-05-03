package com.github.saulmmbp.foroAlura.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.github.saulmmbp.foroAlura.dao.CursoRepository;
import com.github.saulmmbp.foroAlura.dto.CursoDto;
import com.github.saulmmbp.foroAlura.entity.Curso;

@Service
public class CursoService {

	private CursoRepository cursoRepository;

	public CursoService(CursoRepository cursoRepository) {
		this.cursoRepository = cursoRepository;
	}
	
	public List<CursoDto> findAll() {
		return cursoRepository.findAll().stream().map(Curso::toDto).toList();
	}
	
	public CursoDto findById(Long id) {
		return cursoRepository.findById(id).orElseThrow().toDto();
	}
}
