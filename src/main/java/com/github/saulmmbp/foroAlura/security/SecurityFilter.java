package com.github.saulmmbp.foroAlura.security;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.github.saulmmbp.foroAlura.dao.UsuarioRepository;
import com.github.saulmmbp.foroAlura.service.TokenService;

import jakarta.servlet.*;
import jakarta.servlet.http.*;

@Component
public class SecurityFilter extends OncePerRequestFilter {

	private TokenService tokenService;
	private UsuarioRepository usuarioRepository;
	
	public SecurityFilter(TokenService tokenService, UsuarioRepository usuarioRepository) {
		this.tokenService = tokenService;
		this.usuarioRepository = usuarioRepository;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String jwtToken = request.getHeader(HttpHeaders.AUTHORIZATION);
		if (jwtToken != null && !jwtToken.isBlank()) {
			jwtToken = jwtToken.replace("Bearer ", "");
			
			if (tokenService.validateToken(jwtToken)) {
				String subject = tokenService.decodeJWT(jwtToken).getSubject();
				UserDetails usuario = usuarioRepository.findByEmail(subject);
				Authentication auth = new UsernamePasswordAuthenticationToken(usuario, null, 
						usuario == null ? List.of() : usuario.getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(auth);
			}
			
		} else {
			new RuntimeException("Token inválido");
		}
		filterChain.doFilter(request, response);
	}

}
