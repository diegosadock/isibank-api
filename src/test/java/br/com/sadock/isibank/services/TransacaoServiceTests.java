package br.com.sadock.isibank.services;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.sadock.isibank.dto.ExtratoDTO;
import br.com.sadock.isibank.dto.PagamentoDTO;
import br.com.sadock.isibank.dto.TransferenciaDTO;
import br.com.sadock.isibank.exceptions.InvalidAccountException;
import br.com.sadock.isibank.exceptions.InvalidDateIntervalException;
import br.com.sadock.isibank.exceptions.NotEnoughBalanceException;
import br.com.sadock.isibank.model.Transacao;
import br.com.sadock.isibank.service.transacao.ITransacaoService;

@SpringBootTest
public class TransacaoServiceTests {

	//@Mock
	@Autowired
	private ITransacaoService service;

	private PagamentoDTO pagamentoValido;
	private PagamentoDTO pgtoSaldoInsuficiente;
	private PagamentoDTO pgtoComContaInvalida;

	private TransferenciaDTO transferenciaValida;
	private TransferenciaDTO transfOrigemInvalida;
	private TransferenciaDTO transfDestinoInvalido;
	private TransferenciaDTO transfSaldoInsuficiente;
	
	private ExtratoDTO extratoValido;
	private ExtratoDTO extratoContaInvalida;
	private ExtratoDTO extratoDatasInvalidas;

	@BeforeEach
	public void setup() {
		pagamentoValido = new PagamentoDTO(10, LocalDateTime.parse("2025-03-22T15:17:00"), "123456", "Boleto", 100.00);
		pgtoSaldoInsuficiente = new PagamentoDTO(20, LocalDateTime.parse("2025-03-22T15:17:00"), "123456", "Boleto",
				2000.00);
		pgtoComContaInvalida = new PagamentoDTO(100, LocalDateTime.parse("2025-03-22T15:17:00"), "123456", "Boleto",
				100.00);
		transferenciaValida = new TransferenciaDTO(10, 20, LocalDateTime.parse("2025-03-22T15:17:00"), "Manda o pix",
				100.00);
		transfOrigemInvalida = new TransferenciaDTO(100, 20, LocalDateTime.parse("2025-03-22T15:17:00"), "Manda o pix",
				100.00);
		transfDestinoInvalido = new TransferenciaDTO(10, 100, LocalDateTime.parse("2025-03-22T15:17:00"), "Manda o pix",
				100.00);
		transfSaldoInsuficiente = new TransferenciaDTO(10, 20, LocalDateTime.parse("2025-03-22T15:17:00"),
				"Manda o pix", 10000.00);
		
		extratoValido = new ExtratoDTO(10, LocalDateTime.parse("2025-01-01T00:00:00"), LocalDateTime.parse("2025-04-24T23:59:59"));
		extratoContaInvalida = new ExtratoDTO(100, LocalDateTime.parse("2025-01-01T00:00:00"), LocalDateTime.parse("2025-04-24T23:59:59"));
		extratoDatasInvalidas = new ExtratoDTO(10, LocalDateTime.parse("2025-04-24T23:59:59"), LocalDateTime.parse("2025-01-01T00:00:00"));

//		Mockito.when(service.efetuarPagamento(pagamentoValido)).thenReturn(true);
//		Mockito.when(service.efetuarPagamento(pgtoSaldoInsuficiente)).thenThrow(NotEnoughBalanceException.class);
//		Mockito.when(service.efetuarPagamento(pgtoComContaInvalida)).thenThrow(InvalidAccountException.class);
//
//		Mockito.when(service.efetuarTransferencia(transferenciaValida)).thenReturn(true);
//		Mockito.when(service.efetuarTransferencia(transfOrigemInvalida)).thenThrow(InvalidAccountException.class);
//		Mockito.when(service.efetuarTransferencia(transfDestinoInvalido)).thenThrow(InvalidAccountException.class);
//		Mockito.when(service.efetuarTransferencia(transfSaldoInsuficiente)).thenThrow(NotEnoughBalanceException.class);
//		
//		Mockito.when(service.getExtrato(extratoValido)).thenReturn(new ArrayList<Transacao>());
//		Mockito.when(service.getExtrato(extratoContaInvalida)).thenThrow(InvalidAccountException.class);
//		Mockito.when(service.getExtrato(extratoDatasInvalidas)).thenThrow(InvalidDateIntervalException.class);
	}

	@Test
	public void shouldEffectiveDoPayment() {
		assertTrue(service.efetuarPagamento(pagamentoValido));
	}

	@Test
	public void shouldCheckInsufficientBallance() {
		assertThrows(NotEnoughBalanceException.class, () -> {
			service.efetuarPagamento(pgtoSaldoInsuficiente);
		});
	}

	@Test
	public void shouldCheckInvalidAccount() {
		assertThrows(InvalidAccountException.class, () -> {
			service.efetuarPagamento(pgtoComContaInvalida);
		});
	}

	@Test
	public void shouldEffectiveTransfer() {
		assertTrue(service.efetuarTransferencia(transferenciaValida));
	}

	@Test
	public void shouldCheckInvalidSourceAccount() {
		assertThrows(InvalidAccountException.class, () -> {
			service.efetuarTransferencia(transfOrigemInvalida);
		});
	}

	@Test
	public void shouldCheckInvalidDestinationAccount() {
		assertThrows(InvalidAccountException.class, () -> {
			service.efetuarTransferencia(transfDestinoInvalido);
		});
	}

	@Test
	public void shouldCheckInsufficientAccountBallance() {
		assertThrows(NotEnoughBalanceException.class, () -> {
			service.efetuarTransferencia(transfSaldoInsuficiente);
		});
	}
	
	@Test
	public void shouldRetrieveBankStatement() {
		assertNotNull(service.getExtrato(extratoValido));
	}
	
	@Test
	public void shouldCheckInvalidBankStatementAccount() {
		assertThrows(InvalidAccountException.class, () -> {
			service.getExtrato(extratoContaInvalida);
		});
	}
	
	@Test
	public void shouldCheckInvalidBankStatementDateInterval() {
		assertThrows(InvalidDateIntervalException.class, () -> {
			service.getExtrato(extratoDatasInvalidas);
		});
	}

}
