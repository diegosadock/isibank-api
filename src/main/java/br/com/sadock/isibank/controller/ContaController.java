package br.com.sadock.isibank.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.sadock.isibank.dto.ContaDTO;
import br.com.sadock.isibank.dto.ResponseDTO;
import br.com.sadock.isibank.model.Cliente;
import br.com.sadock.isibank.model.Conta;
import br.com.sadock.isibank.service.conta.IContaService;
import jakarta.validation.Valid;

@RestController
@CrossOrigin("*")
public class ContaController {
	
	@Autowired
	private IContaService service;
	
	@PostMapping("/contas")
	public ResponseEntity<ResponseDTO> cadastrarConta(@Valid @RequestBody ContaDTO conta) {
		
		try {
			Integer res = service.cadastrarNovaConta(conta);
			
			if (res != null) {
				return ResponseEntity.status(201).body(new ResponseDTO("Conta cadastrada com sucesso! " + res));
			}
			return ResponseEntity.badRequest().body(new ResponseDTO("Impossível cadastrar conta com dados existentes!"));
			
		}
		catch(Exception ex) {
			return ResponseEntity.badRequest().body(new ResponseDTO("Dados da Conta incompletos!"));
		}	
	}
	
	@GetMapping("/contas/{idConta}")
	public ResponseEntity<Conta> recuperarContasPeloId(@PathVariable Integer idConta) {
		Conta res = service.recuperarPeloNumero(idConta);
		
		if (res != null) {
			return ResponseEntity.ok(res);
		}
		return ResponseEntity.notFound().build();
		
	}

}
