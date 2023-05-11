package com.github.saulmmbp.foroAlura.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import com.github.saulmmbp.foroAlura.dao.*;
import com.github.saulmmbp.foroAlura.dto.request.*;
import com.github.saulmmbp.foroAlura.dto.response.CursoResponse;
import com.github.saulmmbp.foroAlura.entity.*;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CursoService {

	private CursoRepository cursoRepository;
	private UsuarioRepository usuarioRepository;

	public CursoService(CursoRepository cursoRepository, UsuarioRepository usuarioRepository) {
		this.cursoRepository = cursoRepository;
		this.usuarioRepository = usuarioRepository;
	}
	
	public List<CursoResponse> findAll() {
		return cursoRepository.findAll().stream()
				.map(Curso::toResponse)
				.collect(Collectors.toList());
	}
	
	public Page<CursoResponse> findAllPageable(int page, int pageSize, String sortBy, String orderBy) {
		Sort sort = orderBy.equalsIgnoreCase(Sort.Direction.ASC.name()) ? 
				Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
		Pageable pageable = PageRequest.of(page, pageSize, sort);
		return cursoRepository.findAll(pageable).map(Curso::toResponse);
	}
	
	public CursoResponse findById(Long id) {
		return cursoRepository.findById(id).orElseThrow().toResponse();
	}
	
	public CursoResponse save(CursoPostRequest cursoReq) {
		Usuario instructor = usuarioRepository.findById(cursoReq.instructorId()).orElseThrow();
		Curso newCurso = cursoReq.toEntity();
		newCurso.setInstructor(instructor);
		return cursoRepository.save(newCurso).toResponse();
	}
	
	public CursoResponse update(CursoPutRequest cursoReq) {
		Usuario instructor = usuarioRepository.findById(cursoReq.instructorId()).orElseThrow();
		Curso updatedCurso = cursoRepository.findByIdAndInstructor(cursoReq.id(), instructor).orElseThrow();
		updatedCurso.update(cursoReq);
		return cursoRepository.save(updatedCurso).toResponse();
	}
	
	public void delete(Long id) {
		cursoRepository.findById(id).orElseThrow();
		cursoRepository.deleteById(id);
	}
}
