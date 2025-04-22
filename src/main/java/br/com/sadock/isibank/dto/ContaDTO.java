package br.com.sadock.isibank.dto;

import br.com.sadock.isibank.model.Cliente;
import br.com.sadock.isibank.model.Conta;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record ContaDTO(@NotNull Integer numeroBanco, 
					   @NotNull Integer numeroAgencia, 
					   @NotNull @PositiveOrZero Double saldo, 
					   @NotNull @PositiveOrZero Double limite, 
					   @NotNull Integer idCliente) {
	
	public Conta toConta() {
		Conta conta = new Conta();
		conta.setNumeroAgencia(numeroAgencia);
		conta.setNumeroBanco(numeroBanco);
		conta.setSaldo(saldo);
		conta.setLimite(limite);
		conta.setAtiva(1);
		Cliente cliente = new Cliente();
		cliente.setIdCliente(idCliente);
		cliente.setCpf("111");
		cliente.setEmail("email@abc.com");
		cliente.setTelefone("1234");
		conta.setCliente(cliente);
		
		return conta;
	}
}
