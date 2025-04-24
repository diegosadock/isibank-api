package br.com.sadock.isibank.service.conta;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.sadock.isibank.dto.ContaDTO;
import br.com.sadock.isibank.model.Cliente;
import br.com.sadock.isibank.model.Conta;
import br.com.sadock.isibank.repo.ClienteRepo;
import br.com.sadock.isibank.repo.ContaRepo;

@Service
public class ContaServiceImpl implements IContaService {
	
	@Value("${isibank.banknumber}")
	private Integer numeroBanco;
	
	@Autowired
	private ContaRepo contaRepo;
	
	@Autowired
	private ClienteRepo clienteRepo;

	@Override
	public Integer cadastrarNovaConta(ContaDTO nova) {
		// TODO Auto-generated method stub
		Cliente cli = clienteRepo.findById(nova.idCliente()).orElse(null);
		
		if (cli == null) {
			return null;
		}
		Conta conta = nova.toConta();
		conta.setNumeroBanco(numeroBanco);
		//System.out.println(conta.toString());
		return contaRepo.save(conta).getNumeroConta();
	}

	@Override
	public List<Conta> recuperarPeloCliente(Cliente cliente) {
		// TODO Auto-generated method stub
		return contaRepo.findByCliente(cliente);
	}

	@Override
	public Conta recuperarPeloNumero(Integer numeroConta) {
		// TODO Auto-generated method stub
		return contaRepo.findById(numeroConta).orElse(null);
	}

}
