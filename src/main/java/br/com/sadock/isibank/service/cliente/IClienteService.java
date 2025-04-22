package br.com.sadock.isibank.service.cliente;

import br.com.sadock.isibank.dto.ClienteDTO;

public interface IClienteService {
	
	public Integer cadastrarCliente(ClienteDTO novo);
	public Integer alterarDados(ClienteDTO cliente);
	

}
