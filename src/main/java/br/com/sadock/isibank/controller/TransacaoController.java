package br.com.sadock.isibank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.sadock.isibank.dto.PagamentoDTO;
import br.com.sadock.isibank.dto.ResponseDTO;
import br.com.sadock.isibank.dto.TransferenciaDTO;
import br.com.sadock.isibank.service.transacao.ITransacaoService;
import jakarta.validation.Valid;

@RestController
@CrossOrigin("*")
public class TransacaoController {
	
	@Autowired
	private ITransacaoService service;
	
	@PostMapping("/pagamentos")
	public ResponseEntity<ResponseDTO> efetuarPagamento(@Valid @RequestBody PagamentoDTO pagamento) {
		
		try {
			if (service.efetuarPagamento(pagamento)) {
				return ResponseEntity.ok(new ResponseDTO("Pagamento efetuado com sucesso!"));
			}
			return ResponseEntity.badRequest().body(new ResponseDTO("Não foi possível realizar a operação"));
			
			
		}
		catch (Exception ex) {
			return ResponseEntity.badRequest().body(new ResponseDTO(ex.getMessage()));
		}
		
	}
	
	@PostMapping("/transferencias")
	public ResponseEntity<ResponseDTO> efetuarTransferencia(@Valid @RequestBody TransferenciaDTO transferencia) {
		
		try {
			if (service.efetuarTransferencia(transferencia)) {
				return ResponseEntity.ok(new ResponseDTO("Transferencia realizada com sucesso!"));
			}
			return ResponseEntity.badRequest().body(new ResponseDTO("Não foi possível realizar a operação"));
		}
		catch (Exception ex) {
			return ResponseEntity.badRequest().body(new ResponseDTO(ex.getMessage()));
		}
	}

}
