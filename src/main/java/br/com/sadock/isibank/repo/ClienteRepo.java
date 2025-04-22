package br.com.sadock.isibank.repo;

import org.springframework.data.repository.CrudRepository;

import br.com.sadock.isibank.model.Cliente;

public interface ClienteRepo extends CrudRepository<Cliente, Integer> {
	
	public Cliente findByEmailOrCpfOrTelefone(String email, String cpf, String telefone);

}
