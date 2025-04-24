package br.com.sadock.isibank.repo;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.com.sadock.isibank.model.Conta;
import br.com.sadock.isibank.model.Transacao;

public interface TransacaoRepo extends CrudRepository<Transacao, Long> {
	
	public List<Transacao> findByContaAndDataHoraBetween(Conta conta, LocalDateTime inicio, LocalDateTime fim);

}
