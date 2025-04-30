package br.com.sadock.isibank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.sadock.isibank.dto.LoginDTO;
import br.com.sadock.isibank.security.IsiToken;
import br.com.sadock.isibank.service.auth.IAuthService;

@RestController
@CrossOrigin("*")
public class AuthController {
	
	@Autowired
	private IAuthService service;
	
	@PostMapping("/login")
	public ResponseEntity<IsiToken> performLogin(@RequestBody LoginDTO login) {
		IsiToken token = service.authenticate(login.key(), login.senha());
		
		if (token != null) {
			return ResponseEntity.ok(token);
		}
		return ResponseEntity.status(403).build();
	}

}
