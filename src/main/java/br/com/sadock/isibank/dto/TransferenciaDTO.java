package br.com.sadock.isibank.dto;

import java.time.LocalDateTime;

public record TransferenciaDTO(Integer contaOrigem, 
							   Integer contaDestino,
							   LocalDateTime dataHora,
							   String descricao,
							   Double valor) {

}
