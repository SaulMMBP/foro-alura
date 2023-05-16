package com.github.saulmmbp.foroAlura.service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import com.github.saulmmbp.foroAlura.controller.RespuestaController;
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
	
	public Page<RespuestaResponse> findAllPageable(Pageable pageable) {
		return respuestaRepository.findAll(pageable).map(respuesta -> respuesta.toResponse()
				.add(linkTo(methodOn(RespuestaController.class).getRespuestas(pageable)).withRel("respuestas")));
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
		if(!respuestaRepository.existsById(id)) {
			throw new NoSuchElementException();
		}
		respuestaRepository.deleteById(id);
	}
}
