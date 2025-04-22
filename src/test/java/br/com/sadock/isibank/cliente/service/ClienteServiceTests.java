package br.com.sadock.isibank.cliente.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import br.com.sadock.isibank.dto.ClienteDTO;
import br.com.sadock.isibank.service.cliente.IClienteService;
import jakarta.validation.ConstraintViolationException;

@SpringBootTest
public class ClienteServiceTests {
	private ClienteDTO reqValida;
	private ClienteDTO reqInvalida;
	private ClienteDTO reqEmailDupl;
	private ClienteDTO reqCpfDupl;
	private ClienteDTO reqTelDupl;
	private Integer idValido;
	
	@Mock
	private IClienteService service;
	
	@BeforeEach
	public void setup() {
		reqValida = new ClienteDTO("Cliente Válido", "email@email.com", "12345678900", "11987654321", "abc12345");
		reqEmailDupl = new ClienteDTO("Cliente email duplicado", "email@email.com", "12345671200", "11976543234", "12345");
		reqCpfDupl = new ClienteDTO("Cliente cpf duplicado", "cliente@cliente.com", "12345678900", "11976554321", "1234");
		reqTelDupl = new ClienteDTO("Cliente Tel duplicado", "cliente2@cliente2.com", "12347125600", "11987654321", "123456");
		reqInvalida = new ClienteDTO("Cliente Inválido", null, null, null, null);
		idValido = 1;
		
		Mockito.when(service.cadastrarCliente(reqValida)).thenReturn(idValido);
		Mockito.when(service.cadastrarCliente(reqInvalida)).thenThrow(new ConstraintViolationException(null));
		Mockito.when(service.cadastrarCliente(reqEmailDupl)).thenReturn(null);
		Mockito.when(service.cadastrarCliente(reqCpfDupl)).thenReturn(null);
		Mockito.when(service.cadastrarCliente(reqTelDupl)).thenReturn(null);
		
	}
	
	@Test
	public void deveCadastrarClienteValido() {
		assertEquals(service.cadastrarCliente(reqValida), idValido);
		
	}
	
	@Test
	public void deveRejeitarClienteInvalido() {
		assertThrows(ConstraintViolationException.class, () -> {
			service.cadastrarCliente(reqInvalida);
		});
	}
	
	@Test
	public void deveRejeitarClienteEmailDuplicado() {
		assertEquals(service.cadastrarCliente(reqEmailDupl), null);
	}
	
	@Test
	public void deveRejeitarClienteCpfDuplicado() {
		assertEquals(service.cadastrarCliente(reqCpfDupl), null);
	}
	
	@Test
	public void deveRejeitarClienteTelefoneDuplicado() {
		assertEquals(service.cadastrarCliente(reqTelDupl), null);
	}

}
