package br.com.sadock.isibank.service.conta;

import java.util.List;

import br.com.sadock.isibank.dto.ContaDTO;
import br.com.sadock.isibank.model.Cliente;
import br.com.sadock.isibank.model.Conta;

public interface IContaService {
	
	public Integer cadastrarNovaConta(ContaDTO nova);
	public List<Conta> recuperarPeloCliente(Cliente cliente);
	public Conta recuperarPeloNumero(Integer numeroConta);

}
