package com.github.saulmmbp.foroAlura.service;

import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import com.github.saulmmbp.foroAlura.dao.UsuarioRepository;

@Service
public class AuthenticationService implements UserDetailsService {

	private UsuarioRepository usuarioRepository;
	
	public AuthenticationService(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		return usuarioRepository.findByEmail(email).orElseThrow();
	}

}
