package br.com.sadock.isibank.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.com.sadock.isibank.model.Cliente;
import br.com.sadock.isibank.model.Conta;

public interface ContaRepo extends CrudRepository<Conta, Integer> {
	
	public List<Conta> findByCliente(Cliente c);

}
