package br.com.sadock.isibank.api;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import br.com.sadock.isibank.dto.ExtratoDTO;
import br.com.sadock.isibank.dto.PagamentoDTO;
import br.com.sadock.isibank.dto.TransferenciaDTO;

@SpringBootTest
@AutoConfigureMockMvc
public class TransacaoAPITests {
	
	@Autowired
	private MockMvc mvc;
	
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

	}
	
	@Test
	public void shouldPerformPayment() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		String str = mapper.writeValueAsString(pagamentoValido);
		mvc.perform(MockMvcRequestBuilders.post("/pagamentos")
										  .contentType("application/json")
										  .content(str)).andExpect(MockMvcResultMatchers.status().is(200));
										
	}
	
	@Test
	public void shouldInvalidPaymentDueToBalance() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		String str = mapper.writeValueAsString(pgtoSaldoInsuficiente);
		mvc.perform(MockMvcRequestBuilders.post("/pagamentos")
										  .contentType("application/json")
										  .content(str)).andExpect(MockMvcResultMatchers.status().is(400));
	}
	
	@Test
	public void shouldInvalidPaymentDueToDestinationAccount() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		String str = mapper.writeValueAsString(pgtoComContaInvalida);
		mvc.perform(MockMvcRequestBuilders.post("/pagamentos")
										  .contentType("application/json")
										  .content(str)).andExpect(MockMvcResultMatchers.status().is(400));
	}
	
	@Test
	public void shouldPerfomTransfer() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		String str = mapper.writeValueAsString(transferenciaValida);
		mvc.perform(MockMvcRequestBuilders.post("/transferencias")
										  .contentType("application/json")
										  .content(str)).andExpect(MockMvcResultMatchers.status().is(200));
	}
	
	@Test
	public void shouldInvalidTransferDueToSourceAccount() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		String str = mapper.writeValueAsString(transfOrigemInvalida);
		mvc.perform(MockMvcRequestBuilders.post("/transferencias")
										  .contentType("application/json")
										  .content(str)).andExpect(MockMvcResultMatchers.status().is(400));
	}
	
	@Test
	public void shouldInvalidTransferDueToDestinationAccount() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		String str = mapper.writeValueAsString(transfDestinoInvalido);
		mvc.perform(MockMvcRequestBuilders.post("/transferencias")
										  .contentType("application/json")
										  .content(str)).andExpect(MockMvcResultMatchers.status().is(400));
	}
	
	@Test
	public void shouldInvalidTransferDueToBalance() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		String str = mapper.writeValueAsString(transfSaldoInsuficiente);
		mvc.perform(MockMvcRequestBuilders.post("/transferencias")
										  .contentType("application/json")
										  .content(str)).andExpect(MockMvcResultMatchers.status().is(400));
	}

}
