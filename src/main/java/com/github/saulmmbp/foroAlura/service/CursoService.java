package com.github.saulmmbp.foroAlura.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.github.saulmmbp.foroAlura.dao.CursoRepository;
import com.github.saulmmbp.foroAlura.dto.CursoDto;
import com.github.saulmmbp.foroAlura.entity.Curso;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CursoService {

	private CursoRepository cursoRepository;

	public CursoService(CursoRepository cursoRepository) {
		this.cursoRepository = cursoRepository;
	}
	
	public List<CursoDto> findAll() {
		return cursoRepository.findAll().stream()
				.map(Curso::toDto)
				.collect(Collectors.toList());
	}
	
	public CursoDto findById(Long id) {
		return cursoRepository.findById(id).orElseThrow().toDto();
	}
	
	public CursoDto save(CursoDto cursoDto) {
		return cursoRepository.save(cursoDto.toEntity()).toDto();
	}
	
	public CursoDto update(CursoDto cursoDto, Long id) {
		Curso curso = cursoRepository.findById(id).orElseThrow();
		curso.setNombre(cursoDto.nombre());
		curso.setCategoria(cursoDto.categoria());
		return cursoRepository.save(curso).toDto();
	}
	
	public void delete(Long id) {
		cursoRepository.deleteById(id);
	}
}
