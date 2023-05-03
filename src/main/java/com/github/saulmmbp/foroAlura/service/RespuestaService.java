package com.github.saulmmbp.foroAlura.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.github.saulmmbp.foroAlura.dao.RespuestaRepository;
import com.github.saulmmbp.foroAlura.dto.RespuestaDto;
import com.github.saulmmbp.foroAlura.entity.Respuesta;

@Service
public class RespuestaService {

	private RespuestaRepository respuestaRepository;

	public RespuestaService(RespuestaRepository respuestaRepository) {
		this.respuestaRepository = respuestaRepository;
	}
	
	public List<RespuestaDto> findAll() {
		return respuestaRepository.findAll().stream().map(Respuesta::toDto).toList();
	}
	
	public RespuestaDto findById(Long id) {
		return respuestaRepository.findById(id).orElseThrow().toDto();
	}
}
