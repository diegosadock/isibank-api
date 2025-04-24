package br.com.sadock.isibank.security;

import java.security.Key;
import java.util.Collections;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import br.com.sadock.isibank.model.Cliente;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;

public class TokenUtil {
	
	private static final long SEGUNDO = 1000;
	private static final long MINUTO  = 60 * SEGUNDO;
	private static final long HORA    = 60 * MINUTO;
	private static final long DIA     = 24 * HORA;
	public static final long EXPIRATION = 7 * DIA;
	
	public static final String PREFIX = "Bearer ";
	
	public static final String EMISSOR = "IsiFLIX";
	public static final String SECRET_KEY = "01234567890123456789012345678901";
	
	public static IsiToken encode(Cliente cliente) {
		try {
			// definir a chave para criptografar meu JWT
			Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
			// vou gerar um JWT e colocar as informações q eu precisol
			String jwtToken = Jwts.builder().subject(cliente.getNome())
										    .issuer(EMISSOR)
										    .expiration(new Date(System.currentTimeMillis() + EXPIRATION))
										    .signWith(key)
										    .compact();
			IsiToken token = new IsiToken(PREFIX + jwtToken);
			return token;
		} 
		catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	public static Authentication decode(HttpServletRequest request) {
		try {
			String token = request.getHeader("Authorization");
			if (token != null) {
				token = token.replace(PREFIX, "");  // preciso remover o "Bearer" pra só ficar com o JWT
				SecretKey secretKey = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
				
				// vou criar o parser desse token
				JwtParser jwtParser = Jwts.parser().verifyWith(secretKey).build();
				
				// agora vou recuperar cada um dos componentes do jwt (Claims)
				Claims claims = (Claims)jwtParser.parse(token).getPayload();
				
				String issuer = claims.getIssuer();
				String subject = claims.getSubject();
				Date   expiration = claims.getExpiration();
				
				if (isValid(issuer, subject, expiration)) {
					return new UsernamePasswordAuthenticationToken(subject, null, Collections.emptyList());
				}
			}
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
		
	}

	private static boolean isValid(String issuer, String subject, Date expiration2) {
		// TODO Auto-generated method stub
		return issuer.equals(EMISSOR) && subject != null && subject.length() > 0 && expiration2.after(new Date (System.currentTimeMillis()));
	}

}
