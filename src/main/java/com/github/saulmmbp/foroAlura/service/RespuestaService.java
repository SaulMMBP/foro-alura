package com.github.saulmmbp.foroAlura.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.github.saulmmbp.foroAlura.dao.RespuestaRepository;
import com.github.saulmmbp.foroAlura.dto.RespuestaDto;
import com.github.saulmmbp.foroAlura.entity.Respuesta;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class RespuestaService {

	private RespuestaRepository respuestaRepository;

	public RespuestaService(RespuestaRepository respuestaRepository) {
		this.respuestaRepository = respuestaRepository;
	}
	
	public List<RespuestaDto> findAll() {
		return respuestaRepository.findAll().stream()
				.map(Respuesta::toDto)
				.collect(Collectors.toList());
	}
	
	public RespuestaDto findById(Long id) {
		return respuestaRepository.findById(id).orElseThrow().toDto();
	}
	
	public RespuestaDto save(RespuestaDto respuestaDto) {
		return respuestaRepository.save(respuestaDto.toEntity()).toDto();
	}
	
	public RespuestaDto update(RespuestaDto respuestaDto, Long id) {
		Respuesta respuesta = respuestaRepository.findById(id).orElseThrow();
		respuesta.setMensaje(respuestaDto.mensaje());
		respuesta.setSolucion(respuestaDto.solucion());
		return respuestaRepository.save(respuesta).toDto();
	}
	
	public void delete(Long id) {
		respuestaRepository.deleteById(id);
	}
}
