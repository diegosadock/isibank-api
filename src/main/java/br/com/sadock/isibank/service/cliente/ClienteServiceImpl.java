package br.com.sadock.isibank.service.cliente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.sadock.isibank.dto.ClienteDTO;
import br.com.sadock.isibank.model.Cliente;
import br.com.sadock.isibank.repo.ClienteRepo;
import jakarta.validation.Valid;

@Service
public class ClienteServiceImpl implements IClienteService {

	@Autowired
	private ClienteRepo repo;
	
	@Override
	public Integer cadastrarCliente(@Valid ClienteDTO novo) {
		Cliente teste = repo.findByEmailOrCpfOrTelefone(novo.email(), novo.cpf(), novo.telefone());
		
		if (teste != null) {
			return null;
		}
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		Cliente cliente = novo.toCliente();
		String novaSenha = encoder.encode(cliente.getSenha());
		cliente.setSenha(novaSenha);
		
		return repo.save(cliente).getIdCliente();
	}

	@Override
	public Integer alterarDados(ClienteDTO cliente) {
		// TODO Auto-generated method stub
		return repo.save(cliente.toCliente()).getIdCliente();
	}
	
	

}
