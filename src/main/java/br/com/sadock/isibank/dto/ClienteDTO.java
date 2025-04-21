package br.com.sadock.isibank.dto;

import br.com.sadock.isibank.model.Cliente;

public record ClienteDTO(String nome, String email, String cpf, String telefone, String senha) {
	
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
