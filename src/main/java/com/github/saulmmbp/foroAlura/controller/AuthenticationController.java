package com.github.saulmmbp.foroAlura.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.github.saulmmbp.foroAlura.dto.*;
import com.github.saulmmbp.foroAlura.dto.request.AuthenticationRequest;
import com.github.saulmmbp.foroAlura.dto.response.AuthenticationResponse;
import com.github.saulmmbp.foroAlura.entity.Usuario;
import com.github.saulmmbp.foroAlura.service.TokenService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/login")
public class AuthenticationController {

	private AuthenticationManager authManager;
	private TokenService tokenService;
	
	public AuthenticationController(AuthenticationManager authManager, TokenService tokenService) {
		this.authManager = authManager;
		this.tokenService = tokenService;
	}

	@PostMapping
	public ResponseEntity<AuthenticationResponse> authenticateUsuario(@RequestBody @Valid AuthenticationRequest authReq) {
		Authentication authToken = new UsernamePasswordAuthenticationToken(authReq.email(), authReq.contrasena());
		Authentication auth = authManager.authenticate(authToken);
		Usuario authUsuario = (Usuario) auth.getPrincipal();
		String jwtToken = tokenService.generateToken(authUsuario);
		return ResponseEntity.ok(new AuthenticationResponse(jwtToken));
	}
}
