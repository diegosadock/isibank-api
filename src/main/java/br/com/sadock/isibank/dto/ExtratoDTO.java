package br.com.sadock.isibank.dto;

import java.time.LocalDateTime;

public record ExtratoDTO(Integer numeroConta, LocalDateTime inicio, LocalDateTime fim) {

}
