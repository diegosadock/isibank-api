package br.com.sadock.isibank.services;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.sadock.isibank.dto.ContaDTO;
import br.com.sadock.isibank.service.conta.IContaService;

@SpringBootTest
public class ContaServiceTests {
	
	@Autowired
	private IContaService service;
	
	private ContaDTO contaValida;
	private ContaDTO contaInvalida;
	
	@BeforeEach
	public void setup() {
		contaValida = new ContaDTO(1, 1, 100.0, 0.0, 10);
		contaInvalida = new ContaDTO(1, 1, 100.0, 0.0, 100);
	}
	
	@Test
	public void deveriaAceitarContaComClienteExistente() {
		assertNotNull(service.cadastrarNovaConta(contaValida));
	}
	
	@Test
	public void deveriaRejeitarContaComClienteInvalido() {
		assertNull(service.cadastrarNovaConta(contaInvalida));
	}

}
