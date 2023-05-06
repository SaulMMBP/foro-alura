package com.github.saulmmbp.foroAlura.service;

import java.time.*;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.*;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.*;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.github.saulmmbp.foroAlura.entity.Usuario;

import ch.qos.logback.classic.Logger;

@Service
public class TokenService {

	private final String issuer = "foro alura";
	private Logger logger = (Logger) LoggerFactory.getLogger(TokenService.class);
	
	@Value("${api.security.secret}")
	private String secret;
	
	public String generateToken(Usuario usuario) {
		String token = null;
		try {
		    Algorithm algorithm = Algorithm.HMAC256(secret);
		    token = JWT.create()
		        .withIssuer(issuer)
		        .withSubject(usuario.getEmail())
		        .withClaim("id", usuario.getId())
		        .withExpiresAt(generateExpirationDate())
		        .sign(algorithm);
		} catch (JWTCreationException exception){
			logger.error("Error al generar token el token");
		}
		return token;
	}
	
	public boolean validateToken(String token) {
		DecodedJWT decodedJWT = this.decodeJWT(token);
		
		if(decodedJWT != null) {
			return true;
		}
		return false;
	}
	
	public DecodedJWT decodeJWT(String token) {
		DecodedJWT decodedJWT;
		try {
		    Algorithm algorithm = Algorithm.HMAC256(secret);
		    JWTVerifier verifier = JWT.require(algorithm)
		        .withIssuer(issuer)
		        .build();
		        
		    decodedJWT = verifier.verify(token);
		} catch (JWTVerificationException exception){
			logger.info("Request con Token inválido");
			return null;
		}
		return decodedJWT;
	}
	
	private Instant generateExpirationDate() {
		return LocalDateTime.now().plusHours(2).atZone(ZoneId.systemDefault()).toInstant();
	}
	
}
