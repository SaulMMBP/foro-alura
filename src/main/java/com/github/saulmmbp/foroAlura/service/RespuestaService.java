package com.github.saulmmbp.foroAlura.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import com.github.saulmmbp.foroAlura.dao.*;
import com.github.saulmmbp.foroAlura.dto.request.*;
import com.github.saulmmbp.foroAlura.dto.response.RespuestaResponse;
import com.github.saulmmbp.foroAlura.entity.*;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class RespuestaService {

	private RespuestaRepository respuestaRepository;
	private UsuarioRepository usuarioRepository;
	private TopicoRepository topicoRepository;

	public RespuestaService(RespuestaRepository respuestaRepository, UsuarioRepository usuarioRepository,
			TopicoRepository topicoRepository) {
		this.respuestaRepository = respuestaRepository;
		this.usuarioRepository = usuarioRepository;
		this.topicoRepository = topicoRepository;
	}
	
	public List<RespuestaResponse> findAll() {
		return respuestaRepository.findAll().stream()
				.map(Respuesta::toResponse)
				.collect(Collectors.toList());
	}
	
	public Page<RespuestaResponse> findAllPageable(int page, int pageSize, String sortBy, String orderBy) {
		Sort sort = orderBy.equalsIgnoreCase(Sort.Direction.ASC.name()) ? 
				Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
		Pageable pageable = PageRequest.of(page, pageSize, sort);
		return respuestaRepository.findAll(pageable).map(Respuesta::toResponse);
	}
	
	public RespuestaResponse findById(Long id) {
		return respuestaRepository.findById(id).orElseThrow().toResponse();
	}
	
	public RespuestaResponse save(RespuestaPostRequest respuestaReq) {
		Usuario autor = usuarioRepository.findById(respuestaReq.autorId()).orElseThrow();
		Topico topico = topicoRepository.findById(respuestaReq.topicoId()).orElseThrow();
		topico.setEstado(ESTADO.NO_SOLUCIONADO);
		Respuesta respuesta = respuestaReq.toEntity();
		respuesta.setAutor(autor);
		respuesta.setTopico(topico);
		return respuestaRepository.save(respuesta).toResponse();
	}
	
	public RespuestaResponse update(RespuestaPutRequest respuestaReq) {
		Usuario autor = usuarioRepository.findById(respuestaReq.autorId()).orElseThrow();
		Respuesta respuesta = respuestaRepository.findByIdAndAutor(respuestaReq.id(), autor).orElseThrow();
		respuesta.update(respuestaReq);
		return respuestaRepository.save(respuesta).toResponse();
	}
	
	public void delete(Long id) {
		respuestaRepository.findById(id).orElseThrow();
		respuestaRepository.deleteById(id);
	}
}
