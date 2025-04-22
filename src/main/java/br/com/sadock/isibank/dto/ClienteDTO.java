package br.com.sadock.isibank.dto;

import br.com.sadock.isibank.model.Cliente;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ClienteDTO(@NotNull String nome, 
						 @Email String email, 
						 @NotNull String cpf, 
						 @NotNull @Min(11) String telefone, 
						 @NotNull @Min(8) String senha) {
	
	public Cliente toCliente() {
		Cliente cliente = new Cliente();
		cliente.setNome(nome);
		cliente.setEmail(email);
		cliente.setCpf(cpf);
		cliente.setTelefone(telefone);
		cliente.setSenha(senha);
		return cliente;
	}

}
