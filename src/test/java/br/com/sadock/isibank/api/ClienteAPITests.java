package br.com.sadock.isibank.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.assertj.MockMvcTester.MockMvcRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.sadock.isibank.dto.ClienteDTO;

@SpringBootTest
@AutoConfigureMockMvc
public class ClienteAPITests {
	
	@Autowired
	private MockMvc 	mvc;
	
	private ClienteDTO 	reqValida;
	private ClienteDTO 	reqInvalida;
	private ClienteDTO 	reqEmailDupl;
	private ClienteDTO 	reqCpfDupl;
	private ClienteDTO 	reqTelDupl;
	private Integer 	idValido;

	
	@BeforeEach
	public void setup() {
		reqValida = new ClienteDTO("Cliente Válido API", "clienteapi@email.com", "12445658900", "11977644311", "abc123456");
		reqEmailDupl = new ClienteDTO("Cliente email duplicado", "email@cliente.com", "12345671200", "11976543234", "abc123456");
		reqCpfDupl = new ClienteDTO("Cliente cpf duplicado", "cliente@cliente.com", "12345676400", "11976554321", "abc123456");
		reqTelDupl = new ClienteDTO("Cliente Tel duplicado", "cliente2@cliente2.com", "12347125600", "1198754321", "abc123456");
		reqInvalida = new ClienteDTO("Cliente Inválido", null, null, null, null);
		idValido = 1;
	}
	
	@Test
	public void shouldCallAPIForValidCliente() throws Exception {
		ObjectMapper objMapper = new ObjectMapper();
		String str = objMapper.writeValueAsString(reqValida);
		mvc.perform(MockMvcRequestBuilders.post("/clientes")
							.contentType("application/json")
							.content(str)).andExpect(MockMvcResultMatchers.status().is(201));
		
	}
	
	@Test
	public void shouldNotCreateClienteWithDuplicateEmail() throws Exception {
		ObjectMapper objMapper = new ObjectMapper();
		String str = objMapper.writeValueAsString(reqEmailDupl);
		mvc.perform(MockMvcRequestBuilders.post("/clientes")
							.contentType("application/json")
							.content(str)).andExpect(MockMvcResultMatchers.status().is(400));
	}
	
	@Test
	public void shouldNotCreateClienteWithDuplicateCPF() throws Exception {
		ObjectMapper objMapper = new ObjectMapper();
		String str = objMapper.writeValueAsString(reqCpfDupl);
		mvc.perform(MockMvcRequestBuilders.post("/clientes")
							.contentType("application/json")
							.content(str)).andExpect(MockMvcResultMatchers.status().is(400));
	}
	
	@Test
	public void shouldNotCreateClienteWithDuplicatePhone() throws Exception {
		ObjectMapper objMapper = new ObjectMapper();
		String str = objMapper.writeValueAsString(reqTelDupl);
		mvc.perform(MockMvcRequestBuilders.post("/clientes")
							.contentType("application/json")
							.content(str)).andExpect(MockMvcResultMatchers.status().is(400));
	}
}
