package com.github.saulmmbp.foroAlura.service;

import java.time.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.*;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.*;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.github.saulmmbp.foroAlura.entity.Usuario;

@Service
public class TokenService {

	private final String issuer = "foro alura";
	
	@Value("${api.security.secret}")
	private String secret;
	
	public String generateToken(Usuario usuario) {
		try {
		    Algorithm algorithm = Algorithm.HMAC256(secret);
		    return JWT.create()
		        .withIssuer(issuer)
		        .withSubject(usuario.getEmail())
		        .withClaim("id", usuario.getId())
		        .withExpiresAt(generateExpirationDate())
		        .sign(algorithm);
		} catch (JWTCreationException exception){
			throw new RuntimeException();
		}
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
			throw new RuntimeException("DecodedJWT inválido");
		}
		return decodedJWT;
	}
	
	private Instant generateExpirationDate() {
		return LocalDateTime.now().plusHours(2).atZone(ZoneId.systemDefault()).toInstant();
	}
	
}
