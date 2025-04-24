package br.com.sadock.isibank.service.transacao;

import java.util.List;

import br.com.sadock.isibank.dto.ExtratoDTO;
import br.com.sadock.isibank.dto.PagamentoDTO;
import br.com.sadock.isibank.dto.TransferenciaDTO;
import br.com.sadock.isibank.model.Transacao;

public interface ITransacaoService {
	
	public boolean efetuarPagamento(PagamentoDTO pagamento);
	public boolean efetuarTransferencia(TransferenciaDTO transferencia);
	public List<Transacao> getExtrato(ExtratoDTO extrato);

}
