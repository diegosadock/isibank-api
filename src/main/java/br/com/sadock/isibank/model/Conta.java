package br.com.sadock.isibank.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tbl_conta")
public class Conta {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "numero_conta")
	private Integer numeroConta;
	
	@Column(name = "numero_banco", nullable = false)
	private Integer numeroBanco;
	
	@Column(name = "numero_agencia", nullable = false)
	private Integer numeroAgencia;
	
	@Column(name = "saldo", nullable = false)
	private Double saldo;
	
	@Column(name = "limite", nullable = false)
	private Double limite;
	
	@Column(name = "ativa", nullable = false)
	private Integer ativa;
	
	@ManyToOne
	@JoinColumn(name = "tbl_cliente_id_cliente")
	private Cliente cliente;

	public Integer getNumeroConta() {
		return numeroConta;
	}

	public void setNumeroConta(Integer numeroConta) {
		this.numeroConta = numeroConta;
	}

	public Integer getNumeroBanco() {
		return numeroBanco;
	}

	public void setNumeroBanco(Integer numeroBanco) {
		this.numeroBanco = numeroBanco;
	}

	public Integer getNumeroAgencia() {
		return numeroAgencia;
	}

	public void setNumeroAgencia(Integer numeroAgencia) {
		this.numeroAgencia = numeroAgencia;
	}

	public Double getSaldo() {
		return saldo;
	}

	public void setSaldo(Double saldo) {
		this.saldo = saldo;
	}

	public Double getLimite() {
		return limite;
	}

	public void setLimite(Double limite) {
		this.limite = limite;
	}

	public Integer getAtiva() {
		return ativa;
	}

	public void setAtiva(Integer ativa) {
		this.ativa = ativa;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	@Override
	public String toString() {
		return "Conta [numeroConta=" + numeroConta + ", numeroBanco=" + numeroBanco + ", numeroAgencia=" + numeroAgencia
				+ ", saldo=" + saldo + ", limite=" + limite + ", ativa=" + ativa + ", cliente=" + cliente + "]";
	}
	
	

}
