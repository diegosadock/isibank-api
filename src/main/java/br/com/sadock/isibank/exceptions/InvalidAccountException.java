package br.com.sadock.isibank.exceptions;

public class InvalidAccountException extends RuntimeException {
	
	public InvalidAccountException(String msg) {
		super(msg);
	}

}
